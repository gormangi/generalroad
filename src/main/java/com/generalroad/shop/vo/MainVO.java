package com.generalroad.shop.vo;

import com.generalroad.shop.category.vo.CategoryVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MainVO {

    private List<CategoryVO> topCategoryList;

    private List<CategoryVO> childCategoryList;

}
