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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pname;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isFirst = false;

    @OneToMany(mappedBy = "pcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Product> products;

    // 선택 여부
    private boolean isSelected;
    public boolean isFirst() {
        return isFirst;
    }
    public void setFirst(boolean first) {
        isFirst = first;
    }

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
