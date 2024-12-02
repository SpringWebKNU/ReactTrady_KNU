package com.example.trady.service;

import com.example.trady.entity.Member;
import com.example.trady.entity.Product;
import com.example.trady.entity.Selling;

import java.util.List;

public interface SellingService {
    Selling createSelling(Selling selling, Product product, String size, long price);

    // 사용자별 판매 상품 조회 메서드
    List<Selling> findAllByUser(Member user);

    void deleteSelling(Long sellingId);

}