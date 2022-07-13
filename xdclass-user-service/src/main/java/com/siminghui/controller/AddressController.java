package com.siminghui.controller;


import com.siminghui.enums.BizCodeEnum;
import com.siminghui.exception.BizException;
import com.siminghui.model.AddressDO;
import com.siminghui.request.AddressAddRequest;
import com.siminghui.service.AddressService;
import com.siminghui.util.JsonData;
import com.siminghui.vo.AddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 电商-公司收发货地址表 前端控制器
 * </p>
 *
 * @author 十七
 * @since 2022-04-22
 */
@Api(tags = "收货地址模块")
@RestController
@RequestMapping("/api/address/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @ApiOperation("新增收货地址")
    @PostMapping("add")
    public JsonData add(@ApiParam("地址对象") @RequestBody AddressAddRequest addressAddReqeust){

        addressService.add(addressAddReqeust);

        return JsonData.buildSuccess();
    }

    @ApiOperation("根据id查找地址详情")
    @GetMapping("find/{address_id}")
    public Object detail(
            @ApiParam(value = "地址id",required = true)
            @PathVariable("address_id") long addressId){

        AddressVO addressVO = addressService.detail(addressId);

        return addressVO == null ? JsonData.buildResult(BizCodeEnum.ADDRESS_NO_EXITS):JsonData.buildSuccess(addressVO);
    }

    /**
     * 查询用户的全部收货地址
     * @return
     */
    @ApiOperation("查询用户的全部收货地址")
    @GetMapping("/list")
    public JsonData findUserAllAddress(){

        List<AddressVO> list = addressService.listUserAllAddress();

        return JsonData.buildSuccess(list);
    }

}

