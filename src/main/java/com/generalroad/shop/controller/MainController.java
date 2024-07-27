package com.generalroad.shop.controller;

import com.generalroad.shop.service.MainService;
import com.generalroad.shop.vo.MainVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("login")
    @ResponseBody
    public int login(HttpServletRequest request, HttpServletResponse response, @RequestBody String pwCom) {
        if (service.login(pwCom) > 0) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(120*60);
            session.setAttribute("lgsn", pwCom);
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("lginCk")
    @ResponseBody
    public int lginCk(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String lgsn = (String)session.getAttribute("lgsn");
        if (lgsn != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @PostMapping("lgout")
    public void lgout(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.removeAttribute("lgsn");
    }

}
