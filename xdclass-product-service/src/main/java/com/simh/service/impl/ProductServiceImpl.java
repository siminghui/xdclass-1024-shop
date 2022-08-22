package com.simh.service.impl;

import com.simh.model.ProductDO;
import com.simh.mapper.ProductMapper;
import com.simh.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author 司明辉
 * @since 2022-08-22
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductDO> implements ProductService {

}
