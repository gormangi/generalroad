package com.generalroad.shop.banner.vo;

import com.generalroad.shop.common.vo.FileVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerVO {

    private String bannerIdx;

    private String categoryIdx;

    private String categoryName;

    private String bannerTitle;

    private String bannerSubTitle;

    private int bannerOrder;

    private FileVO bannerImgVO;

}
