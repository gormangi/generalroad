package com.generalroad.shop.category.vo;

import com.generalroad.shop.common.vo.FileVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryVO {

    private String categoryIdx;

    private String categoryName;

    private int categoryInPrdcCnt;

    private String parentCategoryIdx;

    private int categoryDept;

    private String categoryUseYn;

    private int childCategoryCnt;

    private int categoryOrder;

    private FileVO categoryThumbnailVO;

    private int categoryInProductCnt;

    private int connectable;
}
