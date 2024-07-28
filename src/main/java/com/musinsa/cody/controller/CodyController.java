package com.musinsa.cody.controller;

import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.service.CodyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodyController {

    private final CodyService codyService;

    @GetMapping("/cody/categories/min-price")
    public ResponseEntity<CategoryMinPriceResponse> getCategoryMinPriceProducts() {
        CategoryMinPriceResponse categoryMinPriceProducts = codyService.getCategoryMinPriceProducts();
        return ResponseEntity.ok(categoryMinPriceProducts);
    }

    @GetMapping("/cody/brands/min-price")
    public ResponseEntity<BrandMinPriceResponse> getBrandMinPriceProducts() {
        BrandMinPriceResponse brandMinPriceProducts = codyService.getBrandMinPriceProducts();
        return ResponseEntity.ok(brandMinPriceProducts);
    }

    // TODO change categoryId to categoryName
    @GetMapping("/cody/{categoryId}/price-range")
    public ResponseEntity<PriceRangeResponse> getCategoryPriceRangeProducts(@PathVariable Long categoryId) {
        PriceRangeResponse categoryPriceRangeProducts = codyService.getCategoryPriceRangeProducts(categoryId);
        return ResponseEntity.ok(categoryPriceRangeProducts);
    }
}
