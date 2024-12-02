package com.example.trady.repository;

import com.example.trady.entity.Member;
import com.example.trady.entity.Product;
import com.example.trady.entity.Selling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SellingRepository extends JpaRepository<Selling, Long> {

    List<Selling> findByUser(Member user);

    void deleteBySproduct(Product product);

    @Query("SELECT MIN(s.sprice) FROM Selling s WHERE s.sproduct.id = :productId")
    Long findLowestPriceByProductId(@Param("productId") Long productId);

    @Query("SELECT s FROM Selling s WHERE s.sproduct.id = :productId AND s.size = :size")
    Selling findByProductAndSize(@Param("productId") Long productId, @Param("size") String size);
}