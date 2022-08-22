package com.simh.service;

import com.simh.model.BannerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simh.vo.BannerVO;

import java.util.List;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author 司明辉
 * @since 2022-08-22
 */
public interface BannerService{

    List<BannerVO> list();

}
