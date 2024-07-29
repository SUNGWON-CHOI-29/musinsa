package com.musinsa.cody.controller;

import com.musinsa.cody.dto.ProductListResponse;
import com.musinsa.cody.dto.ProductRequest;
import com.musinsa.cody.dto.ProductResponse;
import com.musinsa.cody.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    @Operation(summary = "상품 생성 API", description = """
            카테고리아이디, 브랜드아이디, 가격을 입력받아 상품을 생성합니다.
            카테고리아이디 및 브랜드아이디와 일치하는 정보가 없는 경우 상품생성에 실패합니다.
            가격 정보가 0 미만인 경우 상품생성에 실패합니다.
        """)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/products")
    @Operation(summary = "상품리스트 조회 API", description = """
            전체 상품 리스트를 조회합니다.
        """)
    public ResponseEntity<ProductListResponse> getProducts() {
        ProductListResponse productListResponse = productService.getProducts();
        return ResponseEntity.ok(productListResponse);
    }

    @PutMapping("/products/{productId}")
    @Operation(summary = "상품 수정 API", description = """
            상품아이디를 입력받아 카테고리아이디, 브랜드아이디, 가격정보를 수정합니다.
            아이디와 일치하는 상품/카테고리/브랜드가 없는 경우 수정에 실패합니다.
            가격정보가 0 미만인 경우 수정에 실패합니다.
        """)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductRequest request) {
        ProductResponse productResponse = productService.updateProduct(productId, request);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "상품 삭제 API", description = """
            상품아이디를 입력받아 상품을 삭제합니다.
            아이디와 일치하는 상품이 없는 경우 삭제에 실패합니다.
        """)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
