package com.generalroad.shop.service;

import com.generalroad.shop.banner.dao.BannerDAO;
import com.generalroad.shop.banner.vo.BannerVO;
import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import com.generalroad.shop.dao.MainDAO;
import com.generalroad.shop.product.dao.ProductDAO;
import com.generalroad.shop.vo.MainVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {

    private final CategoryDAO categoryDAO;

    private final BannerDAO bannerDAO;

    private final ProductDAO productDAO;

    public MainVO getMainData(){

        MainVO mainVO = new MainVO();
        mainVO.setMainTopCategoryList(categoryDAO.selectMainTopCategoryList());
        mainVO.setMainChildCategoryList(categoryDAO.selectMainChildCategoryList());
        mainVO.setMainCategoryList(categoryDAO.selectMainCategoryList());
        List<BannerVO> bannerList = bannerDAO.selectBannerList();
        bannerList.forEach((item) -> {
            item.setBannerImgVO(bannerDAO.selectFileByPostIdx(item.getBannerIdx()).get(0));
        });
        mainVO.setMainBannerList(bannerList);

        List<Map<String, Object>> productList = new ArrayList<>();
        List<CategoryVO> categoryList = mainVO.getMainCategoryList();
        for (CategoryVO category : categoryList) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("categoryName", category.getCategoryName());
            productMap.put("productList", productDAO.selectProductListByCategoryIdx(category.getCategoryIdx()));
            productList.add(productMap);
        }
        mainVO.setMainProductList(productList);

        return mainVO;
    }

}
