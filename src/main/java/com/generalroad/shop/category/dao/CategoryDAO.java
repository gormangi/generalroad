package com.generalroad.shop.category.dao;

import com.generalroad.shop.category.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDAO {

    List<CategoryVO> selectMainTopCategoryList();

    List<CategoryVO> selectMainChildCategoryList();

    List<CategoryVO> selectAdminTopCategoryList();

    List<CategoryVO> selectAdminChildCategoryList(CategoryVO categoryVO);

    int updateCategoryOrder(CategoryVO topCategoryList);

    CategoryVO selectCategoryInfo(String categoryIdx);

    int updateCategoryInfo(CategoryVO categoryVO);

    int selectTopCategoryCnt();

    int insertTopCategoryInfo(CategoryVO categoryVO);

    int insertChildCategoryInfo(CategoryVO categoryVO);

    int deleteTopCategory(CategoryVO categoryVO);

    int deleteChildCategory(CategoryVO categoryVO);
}
