package com.musinsa.cody.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductRequest {

    @NotNull(message = "브랜드ID를 입력해주세요.")
    private Long brandId;

    @NotNull(message = "카테고리ID를 입력해주세요")
    private Long categoryId;

    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private Long price;
}
