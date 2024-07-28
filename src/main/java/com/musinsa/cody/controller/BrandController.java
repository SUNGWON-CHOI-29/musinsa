package com.musinsa.cody.controller;

import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandRequest;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brands")
    public ResponseEntity<BrandResponse> createBrand(@RequestBody @Valid BrandRequest request) {
        BrandResponse brand = brandService.createBrand(request.getName());
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/brands")
    public ResponseEntity<BrandListResponse> getBrands() {
        BrandListResponse brands = brandService.getBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/brands/{brandId}/name")
    public ResponseEntity<BrandResponse> updateBrandName(@PathVariable Long brandId, @RequestBody @Valid BrandRequest request) {
        BrandResponse brand = brandService.updateBrandName(brandId, request.getName());
        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok().build();
    }
}
