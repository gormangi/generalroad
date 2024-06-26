package com.generalroad.shop.service;

import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.dao.MainDAO;
import com.generalroad.shop.vo.MainVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainDAO mainDao;

    private final CategoryDAO categoryDAO;

    public MainVO getMainData(){

        MainVO mainVO = new MainVO();
        mainVO.setMainTopCategoryList(categoryDAO.selectMainTopCategoryList());
        mainVO.setMainChildCategoryList(categoryDAO.selectMainChildCategoryList());

        return mainVO;
    }

}
