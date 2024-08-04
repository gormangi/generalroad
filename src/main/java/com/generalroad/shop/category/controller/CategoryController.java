package com.generalroad.shop.category.controller;

import com.generalroad.shop.category.service.CategoryService;
import com.generalroad.shop.category.vo.CategoryVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping("/getAdminTopCategoryList")
    @ResponseBody
    public List<CategoryVO> getAdminTopCategoryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return service.getAdminTopCategoryList();
    }

    @PostMapping("/getAdminChildCategoryList")
    @ResponseBody
    public List<CategoryVO> getAdminChildCategoryList(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) {
        return service.getAdminChildCategoryList(categoryVO);
    }

    @PostMapping("/saveCategoryOrder")
    public void saveCategoryOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody List<CategoryVO> topCategoryList) throws Exception {
        service.saveCategoryOrder(topCategoryList);
    }

    @PostMapping("/getCategoryInfo")
    @ResponseBody
    public CategoryVO getCategoryInfo(HttpServletRequest request, HttpServletResponse response,@RequestBody String categoryIdx) throws Exception {
        return service.getCategoryInfo(categoryIdx);
    }

    @PutMapping ("/updateCategoryInfo")
    public void updateCategoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) throws Exception {
        service.updateCategoryInfo(categoryVO);
    }

    @PostMapping("/getTopCategoryCnt")
    @ResponseBody
    public int getTopCategoryCnt(HttpServletRequest request, HttpServletResponse response) {
        return service.getTopCategoryCnt();
    }

    @PostMapping("/insertTopCategoryInfo")
    @ResponseBody
    public String insertTopCategoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) {
        return service.insertTopCategoryInfo(categoryVO);
    }

    @PostMapping("/insertChildCategoryInfo")
    public void insertChildCategoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) {
        service.insertChildCategoryInfo(categoryVO);
    }

    @PostMapping("/deleteTopCategory")
    public void deleteTopCategory(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) throws Exception {
        service.deleteTopCategory(categoryVO);
    }

    @PostMapping("/deleteChildCategory")
    public void deleteChildCategory(HttpServletRequest request, HttpServletResponse response, @RequestBody CategoryVO categoryVO) throws Exception {
        service.deleteChildCategory(categoryVO);
    }

    @PostMapping("/getCategoryProductLinkList")
    @ResponseBody
    public List<CategoryVO> getCategoryProductLinkList(HttpServletRequest request, HttpServletResponse response) {
        return service.getCategoryProductLinkList();
    }

    @PostMapping("/addCategoryPrductLink")
    public void addCategoryPrductLink(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> paramMap) {
        service.addCategoryPrductLink(paramMap);
    }

    @PostMapping("/removeCategoryPrductLink")
    public void removeCategoryPrductLink(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> paramMap) {
        service.removeCategoryPrductLink(paramMap);
    }
}
