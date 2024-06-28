package com.generalroad.shop.controller;

import com.generalroad.shop.service.MainService;
import com.generalroad.shop.vo.MainVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {

    private final MainService service;

    @GetMapping("main")
    @ResponseBody
    public MainVO main(HttpServletRequest request, HttpServletResponse response){
        return service.getMainData();
    }

}
