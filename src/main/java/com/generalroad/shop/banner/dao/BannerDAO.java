package com.generalroad.shop.banner.dao;

import com.generalroad.shop.banner.vo.BannerVO;
import com.generalroad.shop.common.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerDAO {

    List<BannerVO> selectBannerList();

    void updateBannerOrder(BannerVO bannerVO);

    void insertBanner(BannerVO bannerVO);

    void insertBannerFileInfo(FileVO fileVO);

    BannerVO selectBannerInfo(String bannerIdx);

    FileVO selectBannerImg(String bannerIdx);

    List<FileVO> selectFileByPostIdx(String bannerIdx);

    void deleteFile(FileVO fileVO);

    void updateBannerInfo(BannerVO bannerVO);

    void deleteBanner(String bannerIdx);

    List<BannerVO> selectClearList();
}
