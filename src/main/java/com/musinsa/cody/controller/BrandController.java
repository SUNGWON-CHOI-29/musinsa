package com.musinsa.cody.controller;

import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brands")
    public ResponseEntity<BrandResponse> createBrand(@Param("name") String name) {
        BrandResponse brand = brandService.createBrand(name);
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/brands")
    public ResponseEntity<BrandListResponse> getBrands() {
        BrandListResponse brands = brandService.getBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/brands/{brandId}/name")
    public ResponseEntity<BrandResponse> updateBrandName(@PathVariable Long brandId, @Param("name") String name) {
        BrandResponse brand = brandService.updateBrandName(brandId, name);
        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/brands/{brandId}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok().build();
    }
}
