package com.generalroad.shop.product.dao;

import com.generalroad.shop.product.vo.ProductSearchVO;
import com.generalroad.shop.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDAO {

    int selectProductTotalCnt();

    List<ProductVO> selectProductList(ProductSearchVO productSearchVO);

}
