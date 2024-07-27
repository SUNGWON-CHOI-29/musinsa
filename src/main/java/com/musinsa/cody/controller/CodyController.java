package com.musinsa.cody.controller;

import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.service.CodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodyController {

    private final CodyService codyService;

    @GetMapping("/cody/categories/min-price")
    public CategoryMinPriceResponse getCategoryMinPriceProducts() {
        return codyService.getCategoryMinPriceProducts();
    }

    @GetMapping("/cody/brands/min-price")
    public BrandMinPriceResponse getBrandMinPriceProducts() {
        return codyService.getBrandMinPriceProducts();
    }

    @GetMapping("/cody/{categoryId}/price-range")
    public PriceRangeResponse getCategoryPriceRangeProducts(@PathVariable Long categoryId) {
        return codyService.getCategoryPriceRangeProducts(categoryId);
    }
}
