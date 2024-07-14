package com.generalroad.shop.product.service;

import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import com.generalroad.shop.product.dao.ProductDAO;
import com.generalroad.shop.product.vo.ProductChooseSearchVO;
import com.generalroad.shop.product.vo.ProductSearchVO;
import com.generalroad.shop.product.vo.ProductVO;
import com.generalroad.shop.util.CreateUuid;
import com.generalroad.shop.util.OciUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDAO dao;

    public ProductSearchVO getProductList(ProductSearchVO productSearchVO) {
        productSearchVO.getPagination().setTotalDataCnt(dao.selectProductTotalCnt());
        productSearchVO.setProductList(dao.selectProductList(productSearchVO));
        return productSearchVO;
    }

    public ProductChooseSearchVO getProductChooseList(ProductChooseSearchVO productChooseSearchVO) {
        productChooseSearchVO.getPagination().setTotalDataCnt(dao.selectProductChooseTotalCnt(productChooseSearchVO));
        productChooseSearchVO.setProductList(dao.selectProductChooseList(productChooseSearchVO));
        return productChooseSearchVO;
    }

    public void addProduct(ProductVO productVO){

        /********************************** 상품 정보 insert ***********************************/
        productVO.setProductIdx(CreateUuid.createShortUuid());
        dao.insertProduct(productVO);
        /*************************************************************************************/

        /*********************** OCI Object storage 에 이미지 업로드 ***************************/
        Map<String, Object> ociUploadRes = new HashMap<>();
        try{
            ociUploadRes = OciUnit.createObject(productVO.getProductThumbnailVO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*************************************************************************************/

        /******************************** 상품 썸네일 정보 insert *******************************/
        FileVO fileVO = new FileVO();
        fileVO.setFileIdx(CreateUuid.createShortUuid());
        fileVO.setPostIdx(productVO.getProductIdx());
        fileVO.setOriginalFileName(productVO.getProductThumbnailVO().getFileName());
        fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileExtentionName(productVO.getProductThumbnailVO().getFileExtentionName());
        fileVO.setFileSize(productVO.getProductThumbnailVO().getFileSize());
        fileVO.setFileType(productVO.getProductThumbnailVO().getFileType());
        dao.insertProductFileInfo(fileVO);
        /*************************************************************************************/
    }

    public ProductVO getProductInfo(ProductVO productVO) {
        ProductVO productInfo = dao.selectProductInfo(productVO.getProductIdx());
        productInfo.setProductThumbnailVO(dao.selectProductInFileInfo(productVO.getProductIdx()));
        return productInfo;
    }

    public void modifyProduct(ProductVO productVO) throws Exception {
        if (productVO.getProductThumbnailVO().getFileSize() > 0) {  // 새로 등록된 파일이 있는경우 (없으면 상품정보만 update)
            /********************** 파일 업로드 (새로 등록된 파일이 있는경우) **************************/
            List<FileVO> productInFiles = dao.selectFileByPostIdx(productVO.getProductIdx());
            List<String> productFileNames = new ArrayList<>();
            for( FileVO vo : productInFiles ) {
                dao.deleteFile(vo);
                productFileNames.add(vo.getFileName());
            }
            OciUnit.deleteObject(productFileNames);    // oci object storage 에 올라간 물리파일 삭제
            /************************************************************************************/

            /*********************** OCI Object storage 에 이미지 업로드 ***************************/
            Map<String, Object> ociUploadRes = new HashMap<>();
            try{
                ociUploadRes = OciUnit.createObject(productVO.getProductThumbnailVO());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*************************************************************************************/

            /****************************** 상품 이미지 정보 insert *********************************/
            FileVO fileVO = new FileVO();
            fileVO.setFileIdx(CreateUuid.createShortUuid());
            fileVO.setPostIdx(productVO.getProductIdx());
            fileVO.setOriginalFileName(productVO.getProductThumbnailVO().getFileName());
            fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileExtentionName(productVO.getProductThumbnailVO().getFileExtentionName());
            fileVO.setFileSize(productVO.getProductThumbnailVO().getFileSize());
            fileVO.setFileType(productVO.getProductThumbnailVO().getFileType());
            dao.insertProductFileInfo(fileVO);
            /*************************************************************************************/
        }
        dao.updateProductInfo(productVO);
    }

    public List<Map<String, Object>> getCateInProductList(String categoryIdx) {
        return dao.selectCateInProductList(categoryIdx);
    }

    public void saveCateInProductOrder(List<Map<String, Object>> cateInProductList) {
        cateInProductList.forEach(dao::updateCateInProductOrder);
    }

    public void productDelete(String productIdx) throws Exception {
        dao.deleteProduct(productIdx);
        dao.deleteCateInProduct(productIdx);

        List<FileVO> productInFiles = dao.selectFileByPostIdx(productIdx);
        List<String> productFileNames = new ArrayList<>();
        for( FileVO vo : productInFiles ) {
            dao.deleteFile(vo);
            productFileNames.add(vo.getFileName());
        }
        OciUnit.deleteObject(productFileNames);    // oci object storage 에 올라간 물리파일 삭제
    }

}
