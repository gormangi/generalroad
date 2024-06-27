package com.generalroad.shop.category.controller;

import com.generalroad.shop.category.service.CategoryService;
import com.generalroad.shop.category.vo.CategoryVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping("/getTopCategoryList")
    @ResponseBody
    public List<CategoryVO> getTopCategoryList(HttpServletRequest request, HttpServletResponse response){
        return service.getTopCategoryList();
    }
}
