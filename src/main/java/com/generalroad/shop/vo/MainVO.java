package com.generalroad.shop.vo;

import com.generalroad.shop.banner.vo.BannerVO;
import com.generalroad.shop.category.vo.CategoryVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class MainVO {

    private List<CategoryVO> mainTopCategoryList;

    private List<CategoryVO> mainChildCategoryList;

    private List<CategoryVO> mainCategoryList;

    private List<Map<String, Object>> mainProductList;

    private List<BannerVO> mainBannerList;

}
