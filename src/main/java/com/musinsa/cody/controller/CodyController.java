package com.musinsa.cody.controller;

import com.musinsa.cody.common.constant.CategoryEnum;
import com.musinsa.cody.dto.BrandMinPriceResponse;
import com.musinsa.cody.dto.CategoryMinPriceResponse;
import com.musinsa.cody.dto.PriceRangeResponse;
import com.musinsa.cody.service.CodyService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CodyController {

    private final CodyService codyService;

    @GetMapping("/cody/categories/min-price")
    @Operation(summary = "카테고리별최저가 조회 API", description = """
            카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 입니다.
        """)
    public ResponseEntity<CategoryMinPriceResponse> getCategoryMinPriceProducts() {
        CategoryMinPriceResponse categoryMinPriceProducts = codyService.getCategoryMinPriceProducts();
        return ResponseEntity.ok(categoryMinPriceProducts);
    }

    @GetMapping("/cody/brands/min-price")
    @Operation(summary = "최저가브랜드 조회 API", description = """
                단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 
                판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API 입니다.
            """)
    public ResponseEntity<BrandMinPriceResponse> getBrandMinPriceProducts() {
        BrandMinPriceResponse brandMinPriceProducts = codyService.getBrandMinPriceProducts();
        return ResponseEntity.ok(brandMinPriceProducts);
    }

    @GetMapping("/cody/category/price-range")
    @Operation(summary = "카테고리 최저/최고가 상품 조회 API", description = """
                카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API 입니다.
                카테고리이름에 해당하는 카테고리정보가 없는 경우 실패합니다.
            """)
    public ResponseEntity<PriceRangeResponse> getCategoryPriceRangeProducts(@Param("name") String categoryName) {
        Long categoryId = CategoryEnum.getIdByName(categoryName);
        PriceRangeResponse categoryPriceRangeProducts = codyService.getCategoryPriceRangeProducts(categoryId);
        return ResponseEntity.ok(categoryPriceRangeProducts);
    }
}
