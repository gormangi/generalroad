package com.generalroad.shop.product.vo;

import com.generalroad.shop.util.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchVO {

    private List<ProductVO> productList;

    private Pagination pagination;
}
