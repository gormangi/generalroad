package com.generalroad.shop.product.controller;

import com.generalroad.shop.product.service.ProductService;
import com.generalroad.shop.product.vo.ProductSearchVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
