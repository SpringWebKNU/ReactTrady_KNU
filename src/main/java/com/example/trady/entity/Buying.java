package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@ToString
@Slf4j
@NoArgsConstructor
public class Buying {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 회원과의 관계 추가
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    Member user;  // 구매자 (회원)

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Product 삭제 시 Buying도 삭제
    private Product product;  // 상품

    @ManyToOne
    @JoinColumn(name = "product_option_id", referencedColumnName = "id")
    //@OnDelete(action = OnDeleteAction.SET_NULL)
    ProductOption productOption;  // 선택된 옵션


    private String size;  // 상품 옵션의 사이즈
    private long price; // 상품 옵션의 가격


    @Transient  // DB에 저장하지 않겠다는 표시
    private String formattedPrice;

    // Getters and setters
    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public ProductOption getProductOption() {
        if (productOption == null) {
            return getDefaultProductOption();
        }
        return productOption;
    }

    // 옵션 반환
    private ProductOption getDefaultProductOption() {
        return new ProductOption();
    }

    public Buying(Member user, Product product, ProductOption productOption, String size, long price) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.productOption = productOption;
        this.size = size;
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}