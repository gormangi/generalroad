package com.generalroad.shop.category.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryVO {

    private String categoryIdx;

    private String categoryName;

    private String categoryImgSrc;

    private int categoryInPrdcCnt;

    private String parentCategoryIdx;

    private int categoryDept;

    private boolean categoryUseYn;

    private int categoryOrder;

}
