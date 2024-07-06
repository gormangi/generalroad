package com.generalroad.shop.product.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductVO {

    private String productIdx;

    private String productTitle;

    private String productDetail;

    private String productDescription;

    private int productOriginPrice;

    private int productDcPrice;

    private String productCurrencyType;

    private String productRegDate;

    private String productModDate;

}
