package com.generalroad.shop.product.service;

import com.generalroad.shop.product.dao.ProductDAO;
import com.generalroad.shop.product.vo.ProductSearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDAO dao;

    public ProductSearchVO getProductList(ProductSearchVO productSearchVO) {
        productSearchVO.getPagination().setTotalDataCnt(dao.selectProductTotalCnt());
        productSearchVO.setProductList(dao.selectProductList(productSearchVO));
        return productSearchVO;
    }

}
