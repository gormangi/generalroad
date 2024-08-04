package com.generalroad.shop.category.dao;

import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryDAO {

    List<CategoryVO> selectMainTopCategoryList();

    List<CategoryVO> selectMainChildCategoryList();

    List<CategoryVO> selectMainCategoryList();

    List<CategoryVO> selectAdminTopCategoryList();

    List<CategoryVO> selectAdminChildCategoryList(CategoryVO categoryVO);

    void updateCategoryOrder(CategoryVO topCategoryList);

    CategoryVO selectCategoryInfo(String categoryIdx);

    void updateCategoryInfo(CategoryVO categoryVO);

    int selectTopCategoryCnt();

    void insertTopCategoryInfo(CategoryVO categoryVO);

    void insertTopCategoryFileInfo(FileVO fileVO);

    void insertChildCategoryInfo(CategoryVO categoryVO);

    void insertChildCategoryFileInfo(FileVO fileVO);

    void deleteTopCategory(CategoryVO categoryVO);

    void deleteChildCategory(CategoryVO categoryVO);

    List<FileVO> selectFileByPostIdx(String postIdx);

    void deleteFile(FileVO fileVO);

    List<CategoryVO> selectCategoryProductLinkList();

    void insertCategoryPrductLink(Map<String, Object> paramMap);

    void deleteCategoryProductLink(Map<String, Object> paramMap);

    List<CategoryVO> selectClearList();
}
