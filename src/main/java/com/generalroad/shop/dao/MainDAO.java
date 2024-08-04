package com.generalroad.shop.dao;

import com.generalroad.shop.common.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainDAO {

    FileVO selectFileInfo(String postIdx);

    void deleteFileInfo(String fileIdx);

    void deleteCategory(String categoryIdx);

    void deleteProductCate(String categoryIdx);

    void deleteProduct(String productIdx);

    void deleteBanner(String bannerIdx);
}
