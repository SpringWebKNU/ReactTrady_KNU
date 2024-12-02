package com.example.trady.repository;

import com.example.trady.entity.Pcategory;
import com.example.trady.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPnameContaining(String keyword);
    List<Product> findByPcategory(Pcategory pcategory);
}
