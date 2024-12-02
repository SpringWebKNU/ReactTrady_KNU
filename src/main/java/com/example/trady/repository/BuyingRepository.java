package com.example.trady.repository;

import com.example.trady.entity.Buying;
import com.example.trady.entity.Member;
import com.example.trady.entity.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyingRepository extends JpaRepository<Buying,Long> {

    void deleteByProductOptionId(Long productOptionId);

    @Modifying
    @Query("UPDATE Buying b SET b.productOption = NULL WHERE b.productOption.id = :productOptionId")
    void updateProductOptionToDefault(@Param("productOptionId") Long productOptionId);

    // 구매된 상품 목록 조회
    List<Buying> findByProductId(Long productId);
    List<Buying> findByUser(Member user);
}
