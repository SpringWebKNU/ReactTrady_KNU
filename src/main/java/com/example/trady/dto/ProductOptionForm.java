package com.example.trady.dto;

import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class ProductOptionForm {

    private Long id;
    private Product product;
    private String size;
    private long price;
    private boolean isSold = false;  // 기본값은 false (판매되지 않음)

    // 판매된 상품 ->'판매 완료'
    public void markAsSold() {
        this.isSold = true;
    }
    public boolean isSold() {
        return isSold;
    }
    public ProductOption toEntity() {
        return new ProductOption(product, size, price);
    }
    public void logInfo() {
        log.info("id: {}, product: {}, size: {}, price: {}", id, product, size, price);
    }
}