package com.simh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simh.model.BannerDO;
import com.simh.mapper.BannerMapper;
import com.simh.service.BannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simh.vo.BannerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author 司明辉
 * @since 2022-08-22
 */
@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public List<BannerVO> list() {

        List<BannerDO> bannerDOList = bannerMapper.selectList(new QueryWrapper<BannerDO>().orderByAsc("weight"));

        List<BannerVO> bannerVOList = bannerDOList.stream().map(obj -> {
            BannerVO bannerVO = new BannerVO();
            BeanUtils.copyProperties(obj, bannerVO);
            return bannerVO;
        }).collect(Collectors.toList());

        return bannerVOList;
    }
}
