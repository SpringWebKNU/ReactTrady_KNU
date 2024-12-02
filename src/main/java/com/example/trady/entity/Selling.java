package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
@Entity
@ToString
@Slf4j
@NoArgsConstructor
public class Selling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    Member user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product sproduct;

    private String size;
    private long sprice;

    private boolean isSold = false;

    public void markAsSold() {
        this.isSold = true;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        this.isSold = sold;
    }

    public Selling(Member user, Product sproduct, String size, long sprice) {
        this.user = user;
        this.sproduct = sproduct;
        this.size = size;
        this.sprice = sprice;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
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

    public long getSprice() {
        return sprice;
    }

    public void setSprice(long sprice) {
        this.sprice = sprice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
