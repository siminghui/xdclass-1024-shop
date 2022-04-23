package com.siminghui.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siminghui.mapper.AddressMapper;
import com.siminghui.model.AddressDO;
import com.siminghui.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 十七
 * @Date: 2022/4/23 3:21 下午
 * @Description:
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDO detail(Long id) {

        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", id));

        return addressDO;
    }
}
