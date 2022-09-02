package com.simh.service;

import com.simh.model.ProductDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.simh.vo.ProductVO;

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

    /**
     * 查询商品详情接口
     * @param productId
     */
    ProductVO findDetailById(Long productId);
}
