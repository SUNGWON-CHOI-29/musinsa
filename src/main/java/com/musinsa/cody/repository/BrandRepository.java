package com.musinsa.cody.repository;

import com.musinsa.cody.entity.Brand;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("""
            SELECT b
            FROM Brand b
            WHERE    b.isDeleted    = false
            AND      b.isActive     = true
            ORDER BY b.totalPrice ASC
            LIMIT 1
            """)
    Optional<Brand> findMinTotalPriceBrand();

    List<Brand> findAll();

    Optional<Brand> findByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Brand b WHERE b.id = :id")
    Optional<Brand> findByIdWithLock(@Param("id") Long id);
}
