package com.generalroad.shop.service;

import com.generalroad.shop.banner.dao.BannerDAO;
import com.generalroad.shop.banner.vo.BannerVO;
import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import com.generalroad.shop.dao.MainDAO;
import com.generalroad.shop.product.dao.ProductDAO;
import com.generalroad.shop.product.vo.ProductVO;
import com.generalroad.shop.util.OciUnit;
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

    private final MainDAO mainDAO;

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

    public void allClear() throws Exception {
        List<CategoryVO> categoryList = categoryDAO.selectClearList();
        List<ProductVO> productList = productDAO.selectClearList();
        List<BannerVO> bannerList = bannerDAO.selectClearList();

        List<FileVO> clearFileList = new ArrayList<>();
        for (CategoryVO categoryVO : categoryList) {
            clearFileList.add(mainDAO.selectFileInfo(categoryVO.getCategoryIdx()));
            mainDAO.deleteCategory(categoryVO.getCategoryIdx());
            mainDAO.deleteProductCate(categoryVO.getCategoryIdx());
        }
        for (ProductVO productVO : productList) {
            clearFileList.add(mainDAO.selectFileInfo(productVO.getProductIdx()));
            mainDAO.deleteProduct(productVO.getProductIdx());
        }
        for (BannerVO bannerVO : bannerList) {
            clearFileList.add(mainDAO.selectFileInfo(bannerVO.getBannerIdx()));
            mainDAO.deleteBanner(bannerVO.getBannerIdx());
        }

        List<String> fileNames = new ArrayList<>();
        List<String> fileIdxs = new ArrayList<>();
        for (FileVO fileVO : clearFileList) {
            fileNames.add(fileVO.getFileName());
            fileIdxs.add(fileVO.getFileIdx());
        }
        OciUnit.deleteObject(fileNames);

        for (String fileIdx : fileIdxs) {
            mainDAO.deleteFileInfo(fileIdx);
        }
    }

}
