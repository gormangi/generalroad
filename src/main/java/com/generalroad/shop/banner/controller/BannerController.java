package com.generalroad.shop.banner.controller;

import com.generalroad.shop.banner.service.BannerService;
import com.generalroad.shop.banner.vo.BannerVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/banner")
public class BannerController {

    private final BannerService service;

    @PostMapping("/getBannerList")
    @ResponseBody
    public List<BannerVO> getBannerList(HttpServletRequest request, HttpServletResponse response){
        return service.getBannerList();
    }

    @PostMapping("/saveBannerOrder")
    public void saveBannerOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody List<BannerVO> bannerList) {
        service.saveBannerOrder(bannerList);
    }

    @PostMapping("/addBanner")
    public void addBanner(HttpServletRequest request, HttpServletResponse response, @RequestBody BannerVO bannerVO) {
        service.addBanner(bannerVO);
    }

    @PostMapping("/getBannerInfo")
    @ResponseBody
    public BannerVO getBannerInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody String bannerIdx) {
        return service.getBannerInfo(bannerIdx);
    }

    @PostMapping("/modifyBanner")
    public void modifyBanner(HttpServletRequest request, HttpServletResponse response, @RequestBody BannerVO bannerVO) throws Exception {
        service.modifyBanner(bannerVO);
    }

    @PostMapping("/bannerDelete")
    public void bannerDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody String bannerIdx) throws Exception {
        service.bannerDelete(bannerIdx);
    }
}
