package com.simh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simh.enums.AddressStatusEnum;
import com.simh.interceptor.LoginInterceptor;
import com.simh.mapper.AddressMapper;
import com.simh.model.AddressDO;
import com.simh.model.LoginUser;
import com.simh.request.AddressAddRequest;
import com.simh.service.AddressService;
import com.simh.vo.AddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shenyu.client.dubbo.common.annotation.ShenyuDubboClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 十七
 * @Date: 2022/4/23 3:21 下午
 * @Description:
 */
@DubboService
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public AddressVO detail(Long id) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();

        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id",id).eq("user_id",loginUser.getId()));

        if(addressDO == null){
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO,addressVO);

        return addressVO;
    }

    /**
     * 新增收货地址
     *
     *
     *
     * @param addressAddReqeust
     */
    @Override
    public void add(AddressAddRequest addressAddReqeust) {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        addressDO.setUserId(loginUser.getId());

        BeanUtils.copyProperties(addressAddReqeust,addressDO);


        //是否有默认收货地址
        if(addressDO.getDefaultStatus() == AddressStatusEnum.DEFAULT_STATUS.getStatus()){
            //查找数据库是否有默认地址
            AddressDO defaultAddressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>()
                    .eq("user_id",loginUser.getId())
                    .eq("default_status",AddressStatusEnum.DEFAULT_STATUS.getStatus()));

            if(defaultAddressDO != null){
                //修改为非默认收货地址
                defaultAddressDO.setDefaultStatus(AddressStatusEnum.COMMON_STATUS.getStatus());
                addressMapper.update(defaultAddressDO,new QueryWrapper<AddressDO>().eq("id",defaultAddressDO.getId()));
            }
        }

        int rows = addressMapper.insert(addressDO);

        log.info("新增收货地址:rows={},data={}",rows,addressDO);

    }


    /**
     * 根据id删除地址
     * @param addressId
     * @return
     */
    @Override
    public int del(int addressId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        int rows = addressMapper.delete(new QueryWrapper<AddressDO>().eq("id",addressId).eq("user_id",loginUser.getId()));
        return rows;
    }




    /**
     * 查找用全部收货地址
     * @return
     */
    @Override
    public List<AddressVO> listUserAllAddress() {

        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        List<AddressDO> list = addressMapper.selectList(new QueryWrapper<AddressDO>().eq("user_id",loginUser.getId()));

        List<AddressVO> addressVOList =  list.stream().map(obj->{
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(obj,addressVO);
            return addressVO;
        }).collect(Collectors.toList());

        return addressVOList;

    }

    @Override
    @ShenyuDubboClient(path = "/test/shenyu", desc = "测试shenyu")
    public int testDubbo() {
        System.out.println("testDubbo");
        return 1;
    }
}
