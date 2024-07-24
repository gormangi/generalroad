package com.generalroad.shop.banner.service;

import com.generalroad.shop.banner.dao.BannerDAO;
import com.generalroad.shop.banner.vo.BannerVO;
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
public class BannerService {

    private final BannerDAO dao;

    public List<BannerVO> getBannerList() {
        return dao.selectBannerList();
    }

    public void saveBannerOrder(List<BannerVO> bannerList) {
        bannerList.forEach(dao::updateBannerOrder);
    }

    public void addBanner(BannerVO bannerVO) {

        /********************************** 배너 정보 insert ***********************************/
        bannerVO.setBannerIdx(CreateUuid.createShortUuid());
        dao.insertBanner(bannerVO);
        /*************************************************************************************/

        /*********************** OCI Object storage 에 이미지 업로드 ***************************/
        Map<String, Object> ociUploadRes = new HashMap<>();
        try{
            ociUploadRes = OciUnit.createObject(bannerVO.getBannerImgVO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*************************************************************************************/

        /******************************** 배너 이미지 정보 insert *******************************/
        FileVO fileVO = new FileVO();
        fileVO.setFileIdx(CreateUuid.createShortUuid());
        fileVO.setPostIdx(bannerVO.getBannerIdx());
        fileVO.setOriginalFileName(bannerVO.getBannerImgVO().getFileName());
        fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
        fileVO.setFileExtentionName(bannerVO.getBannerImgVO().getFileExtentionName());
        fileVO.setFileSize(bannerVO.getBannerImgVO().getFileSize());
        fileVO.setFileType(bannerVO.getBannerImgVO().getFileType());
        dao.insertBannerFileInfo(fileVO);
        /*************************************************************************************/
    }

    public BannerVO getBannerInfo(String bannerIdx) {
        BannerVO vo = dao.selectBannerInfo(bannerIdx);
        vo.setBannerImgVO(dao.selectBannerImg(bannerIdx));
        return vo;
    }

    public void modifyBanner(BannerVO bannerVO) throws Exception {
        if (bannerVO.getBannerImgVO().getFileSize() > 0) {  // 새로 등록된 파일이 있는경우 (없으면 배너정보만 update)
            /********************** 파일 업로드 (새로 등록된 파일이 있는경우) **************************/
            List<FileVO> productInFiles = dao.selectFileByPostIdx(bannerVO.getBannerIdx());
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
                ociUploadRes = OciUnit.createObject(bannerVO.getBannerImgVO());
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*************************************************************************************/

            /****************************** 상품 이미지 정보 insert *********************************/
            FileVO fileVO = new FileVO();
            fileVO.setFileIdx(CreateUuid.createShortUuid());
            fileVO.setPostIdx(bannerVO.getBannerIdx());
            fileVO.setOriginalFileName(bannerVO.getBannerImgVO().getFileName());
            fileVO.setFilePath(String.valueOf(ociUploadRes.get("uploadBaseUrl")) + String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileName(String.valueOf(ociUploadRes.get("uploadedFileName")));
            fileVO.setFileExtentionName(bannerVO.getBannerImgVO().getFileExtentionName());
            fileVO.setFileSize(bannerVO.getBannerImgVO().getFileSize());
            fileVO.setFileType(bannerVO.getBannerImgVO().getFileType());
            dao.insertBannerFileInfo(fileVO);
            /*************************************************************************************/
        }
        dao.updateBannerInfo(bannerVO);
    }

    public void bannerDelete(String bannerIdx) throws Exception {
        dao.deleteBanner(bannerIdx);

        List<FileVO> productInFiles = dao.selectFileByPostIdx(bannerIdx);
        List<String> productFileNames = new ArrayList<>();
        for( FileVO vo : productInFiles ) {
            dao.deleteFile(vo);
            productFileNames.add(vo.getFileName());
        }
        OciUnit.deleteObject(productFileNames);    // oci object storage 에 올라간 물리파일 삭제
    }
}
