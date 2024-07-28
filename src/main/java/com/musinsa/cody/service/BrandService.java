package com.musinsa.cody.service;

import com.musinsa.cody.common.constant.CodyErrorResult;
import com.musinsa.cody.common.exception.CodyException;
import com.musinsa.cody.dto.BrandListResponse;
import com.musinsa.cody.dto.BrandResponse;
import com.musinsa.cody.entity.Brand;
import com.musinsa.cody.repository.BrandRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public BrandResponse createBrand(String name) {
        try {
            Brand brand = Brand.builder()
                    .name(name)
                    .isActive(false)
                    .isDeleted(false)
                    .totalPrice(0L)
                    .build();
            Brand savedBrand = brandRepository.save(brand);
            return BrandResponse.fromEntity(savedBrand);
        } catch (DataIntegrityViolationException e) {
            throw new CodyException(CodyErrorResult.DUPLICATED_BRAND_NAME);
        }
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
        Optional<Brand> duplicated = brandRepository.findByName(brandName);

        if (duplicated.isPresent()) {
            throw new CodyException(CodyErrorResult.DUPLICATED_BRAND_NAME);
        }

        try {
            Brand brand = brandRepository.findById(brandId)
                    .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));

            brand.changeName(brandName);
            return BrandResponse.fromEntity(brand);
        } catch (DataIntegrityViolationException e) {
            throw new CodyException(CodyErrorResult.DUPLICATED_BRAND_NAME);
        }
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new CodyException(CodyErrorResult.BRAND_NOT_FOUND));
        brand.changeIsDeleted(true);
    }

}
