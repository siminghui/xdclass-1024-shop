package com.simh.service;

import com.simh.model.ProductDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 司明辉
 * @since 2022-08-22
 */
public interface ProductService {

    /**
     * 分页查询商品列表接口
     * @param page
     * @param size
     * @return
     */
    Map<String, Object> page(int page, int size);
}
