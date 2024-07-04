package com.generalroad.shop.category.dao;

import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.io.File;
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

    int insertTopCategoryFileInfo(FileVO fileVO);

    int insertChildCategoryInfo(CategoryVO categoryVO);

    int insertChildCategoryFileInfo(FileVO fileVO);

    int deleteTopCategory(CategoryVO categoryVO);

    int deleteChildCategory(CategoryVO categoryVO);

    List<FileVO> selectFileByPostIdx(String postIdx);

    int deleteFile(FileVO fileVO);
}
