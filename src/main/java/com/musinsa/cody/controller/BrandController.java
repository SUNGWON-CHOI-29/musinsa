package com.musinsa.cody.controller;

import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandRequest;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brands")
    @Operation(summary = "브랜드 생성 API", description = """
            브랜드 명을 입력받아 브랜드를 생성합니다.
            브랜드 명이 중복인 경우 브랜드 생성에 실패합니다.
        """)
    public ResponseEntity<BrandResponse> createBrand(@RequestBody @Valid BrandRequest request) {
        BrandResponse brand = brandService.createBrand(request.getName());
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/brands")
    @Operation(summary = "전체 브랜드 조회 API", description = """
            전체 브랜드 리스트를 조회합니다.
        """)
    public ResponseEntity<BrandListResponse> getBrands() {
        BrandListResponse brands = brandService.getBrands();
        return ResponseEntity.ok(brands);
    }

    @PutMapping("/brands/{brandId}/name")
    @Operation(summary = "브랜드명 수정 API", description = """
            브랜드아이디와 이름을 입력받아 해당 브랜드의 이름을 수정합니다.
            ID와 일치하는 브랜드가 없거나 브랜드명이 중복이면 수정에 실패합니다.
        """)
    public ResponseEntity<BrandResponse> updateBrandName(@PathVariable Long brandId, @RequestBody @Valid BrandRequest request) {
        BrandResponse brand = brandService.updateBrandName(brandId, request.getName());
        return ResponseEntity.ok(brand);
    }

    @DeleteMapping("/brands/{brandId}")
    @Operation(summary = "브랜드 삭제 API", description = """
            브랜드아이디를 입력받아 브랜드를 삭제합니다.
            아이디와 일치하는 브랜드가 없는 경우 삭제에 실패합니다.
        """)
    public ResponseEntity<Void> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.ok().build();
    }
}
