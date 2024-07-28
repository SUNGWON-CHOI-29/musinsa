package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandResponse createBrand(String name) {
        Brand brand = Brand.builder()
                .name(name)
                .isActive(false)
                .isDeleted(false)
                .totalPrice(0L)
                .build();
        Brand savedBrand = brandRepository.save(brand);
        return BrandResponse.fromEntity(savedBrand);
    }

    public BrandListResponse getBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandListResponse.BrandDto> brandDtoList = brands.stream()
                .map(BrandListResponse.BrandDto::fromEntity)
                .toList();

        return BrandListResponse.builder()
                .brandDtoList(brandDtoList)
                .build();
    }

    @Transactional
    public BrandResponse updateBrandName(Long brandId, String brandName) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));
        brand.changeName(brandName);

        return BrandResponse.fromEntity(brand);
    }

    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));
        brand.changeIsDeleted(true);
    }

}
