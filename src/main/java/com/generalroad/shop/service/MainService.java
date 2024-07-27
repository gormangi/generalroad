package com.generalroad.shop.service;

import com.generalroad.shop.banner.dao.BannerDAO;
import com.generalroad.shop.banner.vo.BannerVO;
import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
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

    public int login(String pwCom) {
        if (
            "a01cd8db412368639a8d9d44bf47c5ddbcdd5727ce0313e2292fd83362ff4593bc614ec024d96dabdb4f424c44cb8f3e0068245e84d20be0ec6ed7a36ee92596".equals(pwCom) ||
            "0d5660f2260be493690a84c6787d0c51e3c544891d9aaeda7636f0be88999791a575394cdf861e4bb5b242cec9b0ecbea250cdb3a87f967b462b45bd6960cbdc".equals(pwCom)
        ) {
            return 1;
        } else {
            return 0;
        }
    }

}
