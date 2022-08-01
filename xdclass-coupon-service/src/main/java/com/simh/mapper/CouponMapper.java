package com.simh.mapper;

import com.simh.model.CouponDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 优惠券表 Mapper 接口
 * </p>
 *
 * @author 司明辉
 * @since 2022-07-13
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    /**
     * 扣减存储
     * @param couponId
     * @return
     */
    int reduceStock(@Param("couponId") long couponId);

}
