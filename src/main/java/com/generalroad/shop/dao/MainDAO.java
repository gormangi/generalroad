package com.generalroad.shop.dao;

import com.generalroad.shop.vo.MainVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDAO {

    MainVO selectMainData();

}
