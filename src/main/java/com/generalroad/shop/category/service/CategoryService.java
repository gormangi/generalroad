package com.generalroad.shop.category.service;

import com.generalroad.shop.category.dao.CategoryDAO;
import com.generalroad.shop.category.vo.CategoryVO;
import com.generalroad.shop.common.vo.FileVO;
import com.generalroad.shop.util.CreateUuid;
import com.generalroad.shop.util.OciUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDAO dao;

    public List<CategoryVO> getAdminTopCategoryList() {
        return dao.selectAdminTopCategoryList();
    }

    public List<CategoryVO> getAdminChildCategoryList(CategoryVO categoryVO) {
        return dao.selectAdminChildCategoryList(categoryVO);
    }

    public void saveCategoryOrder(List<CategoryVO> topCategoryList) {
        topCategoryList.forEach(dao::updateCategoryOrder);
    }

    public CategoryVO getCategoryInfo(String categoryIdx) throws Exception {
        CategoryVO vo = dao.selectCategoryInfo(categoryIdx);
        if (vo.getParentCategoryIdx() != null) {
            CategoryVO parentCateVO = dao.selectCategoryInfo(vo.getParentCategoryIdx());
            vo.setParentCategoryName(parentCateVO.getCategoryName());
        }
        List<FileVO> cateInFile = dao.selectFileByPostIdx(categoryIdx);
        vo.setCategoryThumbnailVO(cateInFile.get(0));
        return vo;
    }

    public void updateCategoryInfo(CategoryVO categoryVO) throws Exception {

        if (categoryVO.getCategoryThumbnailVO() != null) {  // 새로 등록된 파일이 있는경우 (없으면 카테고리만 update)
            /********************** 파일 업로드 (새로 등록된 파일이 있는경우) **************************/
            List<FileVO> topCateInFiles = dao.selectFileByPostIdx(categoryVO.getCategoryIdx());
            List<String> cateFileNames = new ArrayList<>();
            for( FileVO vo : topCateInFiles ) {
                dao.deleteFile(vo);
                cateFileNames.add(vo.getFileName());
            }
            OciUnit.deleteObject(cateFileNames);    // oci object storage 에 올라간 물리파일 삭제
            /************************************************************************************/

            /*********************** OCI Object storage 에 이미지 업로드 ***************************/
            Map<String, Object> ociUploadRes = new HashMap<>();
            try{
                ociUploadRes = OciUnit.createObject(categoryVO.getCategoryThumbnailVO());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*************************************************************************************/

            /**************************** 카테고리 이미지 정보 insert *******************************/
            FileVO fileVO = new FileVO();
            fileVO.setFileIdx(CreateUuid.createShortUuid());
            fileVO.setPostIdx(categoryVO.getCategoryIdx());
            fileVO.setOriginalFileName(categoryVO.getCategoryThumbnailVO().getFileName());
            fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileExtentionName(categoryVO.getCategoryThumbnailVO().getFileExtentionName());
            fileVO.setFileSize(categoryVO.getCategoryThumbnailVO().getFileSize());
            fileVO.setFileType(categoryVO.getCategoryThumbnailVO().getFileType());
            dao.insertTopCategoryFileInfo(fileVO);
            /*************************************************************************************/
        }
        dao.updateCategoryInfo(categoryVO); // 카테고리 update
    }

    public int getTopCategoryCnt() {
        return dao.selectTopCategoryCnt();
    }

    public String insertTopCategoryInfo(CategoryVO categoryVO) {

        /******************** 상위카테고리 등록제한(6개) 초과시 업로드 제한 *************************/
        int topCateCnt = dao.selectTopCategoryCnt();
        if (topCateCnt >= 6) return "topCategoryFull";
        /*************************************************************************************/

        /*********************** OCI Object storage 에 이미지 업로드 ***************************/
        Map<String, Object> ociUploadRes = new HashMap<>();
        try{
            ociUploadRes = OciUnit.createObject(categoryVO.getCategoryThumbnailVO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*************************************************************************************/

        /******************************** 카테고리 insert *************************************/
        categoryVO.setCategoryIdx(CreateUuid.createShortUuid());
        dao.insertTopCategoryInfo(categoryVO);
        /*************************************************************************************/

        /**************************** 카테고리 이미지 정보 insert *******************************/
        FileVO fileVO = new FileVO();
        fileVO.setFileIdx(CreateUuid.createShortUuid());
        fileVO.setPostIdx(categoryVO.getCategoryIdx());
        fileVO.setOriginalFileName(categoryVO.getCategoryThumbnailVO().getFileName());
        fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileExtentionName(categoryVO.getCategoryThumbnailVO().getFileExtentionName());
        fileVO.setFileSize(categoryVO.getCategoryThumbnailVO().getFileSize());
        fileVO.setFileType(categoryVO.getCategoryThumbnailVO().getFileType());
        dao.insertTopCategoryFileInfo(fileVO);
        /*************************************************************************************/

        return null;
    }

    public void insertChildCategoryInfo(CategoryVO categoryVO) {

        /*********************** OCI Object storage 에 이미지 업로드 ***************************/
        Map<String, Object> ociUploadRes = new HashMap<>();
        try{
            ociUploadRes = OciUnit.createObject(categoryVO.getCategoryThumbnailVO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*************************************************************************************/

        /******************************** 카테고리 insert *************************************/
        categoryVO.setCategoryIdx(CreateUuid.createShortUuid());
        dao.insertChildCategoryInfo(categoryVO);
        /*************************************************************************************/

        /**************************** 카테고리 이미지 정보 insert *******************************/
        FileVO fileVO = new FileVO();
        fileVO.setFileIdx(CreateUuid.createShortUuid());
        fileVO.setPostIdx(categoryVO.getCategoryIdx());
        fileVO.setOriginalFileName(categoryVO.getCategoryThumbnailVO().getFileName());
        fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileExtentionName(categoryVO.getCategoryThumbnailVO().getFileExtentionName());
        fileVO.setFileSize(categoryVO.getCategoryThumbnailVO().getFileSize());
        fileVO.setFileType(categoryVO.getCategoryThumbnailVO().getFileType());
        dao.insertChildCategoryFileInfo(fileVO);
        /*************************************************************************************/

    }

    public void deleteTopCategory(CategoryVO categoryVO) throws Exception {

        List<FileVO> topCateInFiles = dao.selectFileByPostIdx(categoryVO.getCategoryIdx());
        List<String> cateFileNames = new ArrayList<>();
        for (FileVO topCateFile : topCateInFiles) {
            dao.deleteFile(topCateFile);
            cateFileNames.add(topCateFile.getFileName());
        }
        List<CategoryVO> childCategoryList = dao.selectAdminChildCategoryList(categoryVO);
        for (CategoryVO vo : childCategoryList) {
            List<FileVO> childCateInFiles = dao.selectFileByPostIdx(vo.getCategoryIdx());
            for (FileVO childCateFile : childCateInFiles) {
                dao.deleteFile(childCateFile);
                cateFileNames.add(childCateFile.getFileName());
            }
        }
        OciUnit.deleteObject(cateFileNames);

        dao.deleteTopCategory(categoryVO);
    }

    public void deleteChildCategory(CategoryVO categoryVO) throws Exception {
        List<FileVO> childCateInFiles = dao.selectFileByPostIdx(categoryVO.getCategoryIdx());
        List<String> cateFileNames = new ArrayList<>();
        for (FileVO childCateFile : childCateInFiles) {
            dao.deleteFile(childCateFile);
            cateFileNames.add(childCateFile.getFileName());
        }
        OciUnit.deleteObject(cateFileNames);

        dao.deleteChildCategory(categoryVO);
    }

    public List<CategoryVO> getCategoryProductLinkList() {
        return dao.selectCategoryProductLinkList();
    }

    public void addCategoryPrductLink(Map<String, Object> paramMap) {
        dao.insertCategoryPrductLink(paramMap);
    }

    public void removeCategoryPrductLink(Map<String, Object> paramMap) {
        dao.deleteCategoryProductLink(paramMap);
    }

}
