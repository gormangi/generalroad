package com.generalroad.shop.product.dao;

import com.generalroad.shop.common.vo.FileVO;
import com.generalroad.shop.product.vo.B2CProductSearchVO;
import com.generalroad.shop.product.vo.ProductChooseSearchVO;
import com.generalroad.shop.product.vo.ProductSearchVO;
import com.generalroad.shop.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductDAO {

    int selectProductTotalCnt(ProductSearchVO productSearchVO);

    List<ProductVO> selectProductList(ProductSearchVO productSearchVO);

    int selectProductChooseTotalCnt(ProductChooseSearchVO productChooseSearchVO);

    List<ProductVO> selectProductChooseList(ProductChooseSearchVO productChooseSearchVO);

    void insertProduct(ProductVO productVO);

    void insertProductFileInfo(FileVO fileVO);

    ProductVO selectProductInfo(String productIdx);

    FileVO selectProductInFileInfo(String productIdx);

    List<FileVO> selectFileByPostIdx(String postIdx);

    void deleteFile(FileVO fileVO);

    void updateProductInfo(ProductVO productVO);

    List<Map<String, Object>> selectCateInProductList(String categoryIdx);

    void updateCateInProductOrder(Map<String, Object> cateInProductList);

    void deleteProduct(String productIdx);

    void deleteCateInProduct(String productIdx);

    List<ProductVO> selectProductListByCategoryIdx(String categoryIdx);

    int selectB2CProductTotalCnt(B2CProductSearchVO b2CProductSearchVO);

    List<ProductVO> selectB2CProductList(B2CProductSearchVO b2CProductSearchVO);

    ProductVO selectProductDetail(String productIdx);

}
