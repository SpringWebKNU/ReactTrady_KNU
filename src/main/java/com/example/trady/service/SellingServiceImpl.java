package com.example.trady.service;

import com.example.trady.entity.Member;
import com.example.trady.entity.Product;
import com.example.trady.entity.Selling;
import com.example.trady.repository.ProductOptionRepository;
import com.example.trady.repository.ProductRepository;
import com.example.trady.repository.SellingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellingServiceImpl implements SellingService {

    @Autowired
    private SellingRepository sellingRepository;

    @Autowired
    private ProductOptionService productOptionService;

    @Override
    public void deleteSelling(Long sellingId) {
        // 판매 기록 삭제
        if (sellingRepository.existsById(sellingId)) {
            sellingRepository.deleteById(sellingId);
        } else {
            throw new RuntimeException("해당 판매 기록이 존재하지 않습니다.");
        }
    }

    @Override
    public Selling createSelling(Selling selling, Product product, String size, long price) {
        // 판매 -> 입력할 떄 사이즈,가격 입력
        Selling savedSelling = sellingRepository.save(selling);
        productOptionService.createProductOption(product, size, price);

        return savedSelling;
    }

    @Override
    public List<Selling> findAllByUser(Member user) {
        return sellingRepository.findByUser(user);
    }

}