package com.generalroad.shop.category.service;

import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.util.CreateUuid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDAO dao;

    public List<CategoryVO> getAdminTopCategoryList() {
        return dao.selectAdminTopCategoryList();
    }

    public List<CategoryVO> getAdminChildCategoryList(CategoryVO categoryVO) {
        return dao.selectAdminChildCategoryList(categoryVO);
    }

    public void saveCategoryOrder(List<CategoryVO> topCategoryList) {
        topCategoryList.forEach(dao::updateCategoryOrder);
    }

    public CategoryVO getCategoryInfo(String categoryIdx) {
        return dao.selectCategoryInfo(categoryIdx);
    }

    public void updateCategoryInfo(CategoryVO categoryVO) {
        dao.updateCategoryInfo(categoryVO);
    }

    public int getTopCategoryCnt() {
        return dao.selectTopCategoryCnt();
    }

    public String insertTopCategoryInfo(CategoryVO categoryVO) {

        int topCateCnt = dao.selectTopCategoryCnt();
        if (topCateCnt >= 6) {
            return "topCategoryFull";
        } else {
            categoryVO.setCategoryIdx(CreateUuid.createShortUuid());
            dao.insertTopCategoryInfo(categoryVO);
        }
        return null;
    }

    public void insertChildCategoryInfo(CategoryVO categoryVO) {
        categoryVO.setCategoryIdx(CreateUuid.createShortUuid());
        dao.insertChildCategoryInfo(categoryVO);
    }

    public void deleteTopCategory(CategoryVO categoryVO) {
        dao.deleteTopCategory(categoryVO);
    }

    public void deleteChildCategory(CategoryVO categoryVO) {
        dao.deleteChildCategory(categoryVO);
    }

}
