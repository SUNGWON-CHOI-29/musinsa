package com.musinsa.cody.service;

import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandListResponse getBrands() {
        List<Brand> allByIsDeletedFalse = brandRepository.findAllByIsDeletedFalse();
        List<BrandListResponse.BrandDto> brandDtoList = allByIsDeletedFalse.stream()
                .map(BrandListResponse.BrandDto::fromEntity)
                .toList();

        return BrandListResponse.builder()
                .brandDtoList(brandDtoList)
                .build();
    }

    public BrandResponse updateBrandName(Long brandId, String brandName) {
        brandRepository.updateNameById(brandId, brandName);
        Optional<Brand> brand = brandRepository.findById(brandId);
        return BrandResponse.fromEntity(brand.get());
    }

    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

}
