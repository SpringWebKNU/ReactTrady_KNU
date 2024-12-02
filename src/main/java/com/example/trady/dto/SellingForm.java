package com.example.trady.dto;

import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import com.example.trady.entity.Selling;
import com.example.trady.entity.Member;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
public class SellingForm {

    Long id;
    Member member;
    Product sproduct;
    String size;
    long sprice;

    private boolean isSold = false;  // 판매 상태 (기본값: false)

    // 판매 완료 상태로 변경하는 메소드
    public void markAsSold() {
        this.isSold = true;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        this.isSold = sold;
    }

    public SellingForm(Long id, Member member, Product sproduct, String size, long sprice) {
        this.id = id;
        this.member = member;  // Pass member here
        this.sproduct = sproduct;
        this.size = size;
        this.sprice = sprice;
    }

    public Selling toEntity() {
        return new Selling(member, sproduct, size, sprice);  // Pass member to entity
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getSproduct() {
        return sproduct;
    }

    public void setSproduct(Product sproduct) {
        this.sproduct = sproduct;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public long getSprice() {
        return sprice;
    }

    public void setSprice(long sprice) {
        this.sprice = sprice;
    }
}
