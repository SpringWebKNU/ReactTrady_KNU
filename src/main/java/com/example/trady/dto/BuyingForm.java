package com.example.trady.dto;

import com.example.trady.entity.Buying;
import com.example.trady.entity.Member;
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
public class BuyingForm {

    Long id;
    Member member;
    Product product;
    ProductOption productOption;  // 선택된 옵션
    String size;
    long price;

    public Buying toEntity() {
        return new Buying(member, product, productOption,size,price);  // Pass member to entity
    }
}
