package com.example.trady.service;

import com.example.trady.dto.ProductForm;
import com.example.trady.entity.Pcategory;
import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {
   @Transactional
   Product saveProduct(ProductForm productForm);

   @Transactional
   Product updateProduct(ProductForm productForm);

   Product findProductById(Long id);

   // 특정 상품 삭제
   @Transactional
   void deleteProduct(Long id);

   // 검색 기능
   List<Product> search(String keyword);

   // 모든 상품 및 카테고리
   List<Product> findAllProducts();
   List<Pcategory> findAllCategories();
   Map<Long, List<Product>> groupProductsByCategory();

   // 파일 저장 (상대 경로 반환)
   String saveFile(MultipartFile file);

   // 가격 형식 원화~~
   void updateFormattedPrice(Long id, String formattedPrice);
   List<ProductOption> getProductOptions(Long productId);

}