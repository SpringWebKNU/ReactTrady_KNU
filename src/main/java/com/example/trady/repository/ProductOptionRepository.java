package com.example.trady.repository;

import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    List<ProductOption> findByProductId(Long productId);
    ProductOption findByProductAndSize(Product product, String size);
    // 전체 옵션테이블 올라온거 조회
    List<ProductOption> findByProduct(Product product);

    @Query("SELECT MIN(po.price) FROM ProductOption po WHERE po.product.id = :productId")
    Long findLowestPriceByProductId(@Param("productId") Long productId);

    //boolean existsByProductIdAndSize(Long productId, String size);

    @Modifying
    @Transactional
    @Query("UPDATE ProductOption p SET p.isSold = true WHERE p.id = :id")
    void markAsSold(@Param("id") Long id);

}
