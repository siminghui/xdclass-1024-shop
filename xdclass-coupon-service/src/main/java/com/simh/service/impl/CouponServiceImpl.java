package com.simh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simh.enums.BizCodeEnum;
import com.simh.enums.CouponCategoryEnum;
import com.simh.enums.CouponPublishEnum;
import com.simh.enums.CouponStateEnum;
import com.simh.exception.BizException;
import com.simh.interceptor.LoginInterceptor;
import com.simh.mapper.CouponMapper;
import com.simh.mapper.CouponRecordMapper;
import com.simh.model.CouponDO;
import com.simh.model.CouponRecordDO;
import com.simh.model.LoginUser;
import com.simh.request.NewUserCouponRequest;
import com.simh.service.CouponService;
import com.simh.util.CommonUtil;
import com.simh.util.JsonData;
import com.simh.vo.CouponVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: 十七
 * @Date: 2022/7/13 10:27 下午
 * @Description:
 */
@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {

        Page<CouponDO> pageInfo = new Page<>(page, size);
        IPage<CouponDO> couponDOIPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>()
                .eq("publish", CouponPublishEnum.PUBLISH)
                .eq("category", CouponCategoryEnum.PROMOTION)
                .orderByDesc("create_time"));

        Map<String, Object> pageMap = new HashMap<>(3);
        pageMap.put("total_record", couponDOIPage.getTotal());
        pageMap.put("total_page", couponDOIPage.getPages());
        pageMap.put("current_data", couponDOIPage.getRecords().stream().map(this::BeanProcess).collect(Collectors.toList()));

        return pageMap;
    }


    /**
     * 领劵接口
     * 1、获取优惠券是否存在
     * 2、校验优惠券是否可以领取：时间、库存、超过限制
     * 3、扣减库存
     * 4、保存领劵记录
     *
     * @param couponId
     * @param category
     * @return
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public JsonData addCoupon(long couponId, CouponCategoryEnum category) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        String lockKey = "lock:coupon:"+couponId;
        RLock rLock = redissonClient.getLock(lockKey);

        //多个线程进入，会阻塞等待释放锁 默认30s 后续会有watch dog自动续期
        rLock.lock();

        // 加锁10秒钟过期，没有watch dog功能，无法 自动续期
        rLock.lock(10, TimeUnit.SECONDS);

        log.info("领劵接口加锁成功:{}",Thread.currentThread().getId());
        try{

            CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>()
                    .eq("id", couponId)
                    .eq("category", category.name()));

            //优惠券是否可以领取
            this.checkCoupon(couponDO, loginUser.getId());

            //构建领劵记录
            CouponRecordDO couponRecordDO = new CouponRecordDO();
            BeanUtils.copyProperties(couponDO, couponRecordDO);
            couponRecordDO.setCreateTime(new Date());
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setUserName(loginUser.getName());
            couponRecordDO.setCouponId(couponId);
            couponRecordDO.setId(null);

            //扣减库存
            int rows = couponMapper.reduceStock(couponId);

            if (rows == 1) {
                //库存扣减成功才保存记录
                couponRecordMapper.insert(couponRecordDO);

            } else {
                log.warn("发放优惠券失败:{},用户:{}", couponDO, loginUser);

                throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
            }

        } finally {
            rLock.unlock();
            log.info("解锁成功");
        }

        return JsonData.buildSuccess();

    }

    /**
     * 用户微服务调用的时候没有传递token，未登录
     *
     * 本地直接调用发放优惠券的方法，需要构造一个登录用户存储在threadLocal
     *
     * @param newUserCouponRequest
     * @return
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public JsonData initNewUserCoupon(NewUserCouponRequest newUserCouponRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(newUserCouponRequest.getUserId());
        loginUser.setName(newUserCouponRequest.getName());
        LoginInterceptor.threadLocal.set(loginUser);

        // 查询新用户有哪些优惠券
        List<CouponDO> couponDOList = couponMapper.selectList(new QueryWrapper<CouponDO>().eq("category", CouponCategoryEnum.NEW_USER.name()));

        for (CouponDO couponDO : couponDOList) {
            // 幂等操作，调用需要加锁
            this.addCoupon(couponDO.getId(), CouponCategoryEnum.NEW_USER);
        }

        return JsonData.buildSuccess();
    }


    /**
     * 校验是否可以领取
     * @param couponDO
     * @param userId
     */
    private void checkCoupon(CouponDO couponDO, Long userId) {

        if(couponDO==null){
            throw new BizException(BizCodeEnum.COUPON_NO_EXITS);
        }

        //库存是否足够
        if(couponDO.getStock()<=0){
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }

        //判断是否是否发布状态
        if(!couponDO.getPublish().equals(CouponPublishEnum.PUBLISH.name())){
            throw new BizException(BizCodeEnum.COUPON_GET_FAIL);
        }

        //是否在领取时间范围
        long time = CommonUtil.getCurrentTimestamp();
        long start = couponDO.getStartTime().getTime();
        long end = couponDO.getEndTime().getTime();
        if(time<start || time>end){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }

        //用户是否超过限制
        int recordNum =  couponRecordMapper.selectCount(new QueryWrapper<CouponRecordDO>()
                .eq("coupon_id",couponDO.getId())
                .eq("user_id",userId));

        if(recordNum >= couponDO.getUserLimit()){
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }

    }


    private CouponVO BeanProcess(CouponDO obj) {
        CouponVO couponVO = new CouponVO();
        BeanUtils.copyProperties(obj, couponVO);
        return couponVO;
    }
}
