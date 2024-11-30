package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Slf4j
@NoArgsConstructor
public class Pcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY로 자동 생성
    private Long id; // id 필드 이름 일치 (기존 pid → id)

    private String pname; // 카테고리 이름

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isFirst = false; // 기본값을 false로 설정

    // One-to-Many 관계 추가 (양방향)
    @OneToMany(mappedBy = "pcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Product> products;

    // 선택 여부를 나타내는 필드
    private boolean isSelected;  // 카테고리가 선택되었는지 여부


    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }


    // Constructor
    public Pcategory(Long id, String pname) {
        this.id = id;
        this.pname = pname;
    }

    public void logInfo() {
        log.info("id: {}, pname: {}", id, pname);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
