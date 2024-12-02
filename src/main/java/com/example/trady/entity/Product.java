package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Entity @ToString @Slf4j @NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String pname;
    private String pimg;
    private String formattedPrice; // 가격 포맷해서 표시하려고 만듬

    @ManyToOne
    @JoinColumn(name = "pcategory_id",  referencedColumnName = "id")
    private Pcategory pcategory;

    @Column(nullable = false)
    private LocalDateTime pdate;

    public String getPcategoryName() {
        return pcategory != null ? pcategory.getPname() : "카테고리 없음";
    }

    public boolean isInCategory(Long categoryId) {
        return this.pcategory != null && this.pcategory.getId().equals(categoryId);
    }

    public Product(Long id, String pname, String pimg, Pcategory pcategory, LocalDateTime pdate) {
        this.id = id;
        this.pname = pname;
        this.pimg = pimg;
        this.pcategory = pcategory;
        this.pdate = pdate;
    }

    public Long getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public String getPimg() {
        return pimg;
    }

    public Pcategory getPcategory() {
        return pcategory;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public void setPcategory(Pcategory pcategory) {
        this.pcategory = pcategory;
    }

    public LocalDateTime getPdate() {
        return pdate;
    }

    public void setPdate(LocalDateTime pdate) {
        this.pdate = pdate;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public void logInfo(){
        log.info("id: {}, pname: {}, pimg: {}", id, pname, pimg);
    }

}
