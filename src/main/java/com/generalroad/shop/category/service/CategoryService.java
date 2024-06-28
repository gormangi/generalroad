package com.generalroad.shop.category.service;

import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDAO dao;

    public List<CategoryVO> getTopCategoryList() {
        return dao.selectTopCategoryList();
    }

    public void saveTopCategoryOrder(List<CategoryVO> topCategoryList) {
        topCategoryList.forEach(dao::updateTopCategoryOrder);
    }

}
