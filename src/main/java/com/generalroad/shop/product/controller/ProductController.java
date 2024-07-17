package com.generalroad.shop.product.controller;

import com.generalroad.shop.product.service.ProductService;
import com.generalroad.shop.product.vo.B2CProductSearchVO;
import com.generalroad.shop.product.vo.ProductChooseSearchVO;
import com.generalroad.shop.product.vo.ProductSearchVO;
import com.generalroad.shop.product.vo.ProductVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @PostMapping("/getProductList")
    @ResponseBody
    public ProductSearchVO getProductList(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductSearchVO productSearchVO){
        return service.getProductList(productSearchVO);
    }

    @PostMapping("/getProductChooseList")
    @ResponseBody
    public ProductChooseSearchVO getProductChooseList(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductChooseSearchVO productChooseSearchVO){
        return service.getProductChooseList(productChooseSearchVO);
    }

    @PostMapping("/addProduct")
    public void addProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductVO productVO){
        service.addProduct(productVO);
    }

    @PostMapping("/getProductInfo")
    @ResponseBody
    public ProductVO getProductInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductVO productVO) {
        return service.getProductInfo(productVO);
    }

    @PostMapping("/modifyProduct")
    public void modifyProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductVO productVO) throws Exception {
        service.modifyProduct(productVO);
    }

    @PostMapping("/getCateInProductList")
    @ResponseBody
    public List<Map<String, Object>> getCateInProductList(HttpServletRequest request, HttpServletResponse response, @RequestBody String categoryIdx) {
        return service.getCateInProductList(categoryIdx);
    }

    @PostMapping("/saveCateInProductOrder")
    public void saveCateInProductOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody List<Map<String, Object>> cateInProductList) {
        service.saveCateInProductOrder(cateInProductList);
    }

    @PostMapping("/productDelete")
    public void productDelete(HttpServletRequest request, HttpServletResponse response, @RequestBody String productIdx) throws Exception {
        service.productDelete(productIdx);
    }

    @PostMapping("/getB2CProductList")
    @ResponseBody
    public B2CProductSearchVO getB2CProductList(HttpServletRequest request, HttpServletResponse response, @RequestBody B2CProductSearchVO b2CProductSearchVO) {
        return service.getB2CProductList(b2CProductSearchVO);
    }

}
