package com.musinsa.cody.repository;

import com.musinsa.cody.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    List<Brand> findAllByIsDeletedFalse();

    @Modifying
    @Transactional
    @Query("""
           UPDATE Brand b
           SET b.isDeleted = true
           WHERE b.id = :id
           """)
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("""
           UPDATE Brand b
           SET b.name = :name
           WHERE b.id = :id
           """)
    int updateNameById(@Param("id") Long id, @Param("name") String name);
}
