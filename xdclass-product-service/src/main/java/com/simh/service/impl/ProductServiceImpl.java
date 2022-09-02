package com.simh.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simh.model.ProductDO;
import com.simh.mapper.ProductMapper;
import com.simh.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.simh.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 司明辉
 * @since 2022-08-22
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Map<String, Object> page(int page, int size) {


        Page<ProductDO> pageInfo = new Page<>(page, size);

        IPage<ProductDO> productDOPage = productMapper.selectPage(pageInfo, null);

        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", productDOPage.getTotal());
        pageMap.put("total_page", productDOPage.getPages());
        pageMap.put("current_data", productDOPage.getRecords().stream().map(this::beanProcess).collect(Collectors.toList()));

        return pageMap;
    }

    @Override
    public ProductVO findDetailById(Long productId) {
        ProductDO productDO = productMapper.selectById(productId);
        return beanProcess(productDO);
    }

    @Override
    public List<ProductVO> findProductByIdBatch(List<Long> productIdList) {

        List<ProductDO> productDOList = productMapper.selectList(new QueryWrapper<ProductDO>().eq("id", productIdList));
        return productDOList.stream().map(this::beanProcess).collect(Collectors.toList());

    }

    private ProductVO beanProcess(ProductDO productDO) {

        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productDO, productVO);
        productVO.setStock(productDO.getStock() - productDO.getLockStock());
        return productVO;

    }
}
