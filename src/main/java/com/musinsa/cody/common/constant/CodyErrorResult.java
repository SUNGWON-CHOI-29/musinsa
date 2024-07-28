package com.musinsa.cody.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CodyErrorResult {

    BRAND_NOT_FOUND(HttpStatus.BAD_REQUEST, "브랜드 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "카테고리 정보를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "상품정보를 찾을 수 없습니다."),
    MIN_PRICE_BRAND_NOT_FOUND(HttpStatus.BAD_REQUEST, "최저가 브랜드 정보를 찾을 수 없습니다."),
    MIN_PRICE_PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "카테고리 최저가 상품을 찾을 수 없습니다."),
    MAX_PRICE_PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "카테고리 최고가 상품을 찾을 수 없습니다."),
    DUPLICATED_BRAND_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 브랜드 명입니다."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
