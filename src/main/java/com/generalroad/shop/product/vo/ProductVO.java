package com.generalroad.shop.product.vo;

import com.generalroad.shop.common.vo.FileVO;
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

    private FileVO productThumbnailVO;

    private String productImgPath;

    private String choosedProduct;

}
