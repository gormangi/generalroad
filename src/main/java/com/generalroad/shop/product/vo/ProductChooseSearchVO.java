package com.generalroad.shop.product.vo;

import com.generalroad.shop.util.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductChooseSearchVO {

    private List<ProductVO> productList;

    private Pagination pagination;

    private String categoryIdx;

    private String searchKeyword;

}
