package com.generalroad.shop.service;

import com.generalroad.shop.dao.MainDAO;
import com.generalroad.shop.vo.MainVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

    private final MainDAO dao;

    public MainVO getMainData(){
        return dao.selectMainData();
    }

}
