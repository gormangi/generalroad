package com.generalroad.shop.category.dao;

import com.generalroad.shop.category.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDAO {

    List<CategoryVO> selectCategoryList();
}
