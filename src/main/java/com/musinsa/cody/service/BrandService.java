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

    public BrandResponse updateBrandName(Long brandId, String brandName) {
        brandRepository.updateNameById(brandId, brandName);
        Optional<Brand> brand = brandRepository.findById(brandId);
        return BrandResponse.fromEntity(brand.get());
    }

    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

}
