package com.musinsa.cody.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BrandRequest {

    @NotBlank(message = "브랜드명을 입력해주세요.")
    private String name;
}
