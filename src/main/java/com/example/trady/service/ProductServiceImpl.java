package com.example.trady.service;

import com.example.trady.dto.ProductForm;
import com.example.trady.entity.Pcategory;
import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import com.example.trady.repository.*;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PcategoryRepository pcategoryRepository;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private SellingRepository sellingRepository;
    @Autowired
    private BuyingRepository buyingRepository;
    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private  ProductOptionService productOptionService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PcategoryRepository pcategoryRepository) {
        this.productRepository = productRepository;
        this.pcategoryRepository = pcategoryRepository;
    }

    @Override
    public Product saveProduct(ProductForm productForm) {
        Pcategory pcategory = pcategoryRepository.findById(productForm.getCategoryId())
                .orElseThrow(() -> new RuntimeException("xxxx"));

        // 파일 처리
        String filePath = saveFile(productForm.getPimg());
        Product product = productForm.toEntity(pcategory, filePath);

       // null 일때 현재 시간 반환해서 입력할 때 날짜 칸없어서 현재 시간 반환해요
        if (product.getPdate() == null) {
            product.setPdate(LocalDateTime.now()); 
        }

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductForm productForm) {
        Pcategory pcategory = pcategoryRepository.findById(productForm.getCategoryId())
                .orElseThrow(() -> new RuntimeException("categoryxx: " + productForm.getCategoryId()));

        Product existingProduct = productRepository.findById(productForm.getId())
                .orElseThrow(() -> new RuntimeException("Productxx: " + productForm.getId()));

        // 파일 처리
        String filePath = saveFile(productForm.getPimg()); // MultipartFile을 처리
        existingProduct.setPname(productForm.getPname());
        existingProduct.setPcategory(pcategory);

        if (productForm.getPdate() != null) {
            existingProduct.setPdate(productForm.getPdate());
        } else {
            existingProduct.setPdate(LocalDateTime.now()); // pdate가 null이면 현재 시간!
        }

        if (filePath != null) {
            existingProduct.setPimg(filePath);
        }

        return productRepository.save(existingProduct);
    }

    public String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            // 업로드 디렉토리를 "C:/Trady/uploads"로 설정
            String uploadDir = "C:/Trady/src/main/resources/static/images/uploads";  // 절대 경로 설정

            // 파일에 랜덤이름 앞에 붙혀줘서 겹치는 오류 출동? 방지
            String fileName = UUID.randomUUID().toString().substring(0, 8) + "_" + file.getOriginalFilename();
            File destinationFile = new File(uploadDir, fileName);

            // 폴더 없으면 생성!!
            if (!destinationFile.getParentFile().exists()) {
                destinationFile.getParentFile().mkdirs();  // 디렉토리 생성
            }

            try {
                // 파일을 해당 경로에 저장
                file.transferTo(destinationFile);
                return fileName;
            } catch (IOException e) {
                throw new RuntimeException("File upload failed", e);
            }
        }
        return null;
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 상품 삭제하면 밑에 있던것도 다 삭제해야 삭제되욤. 아니면 테이블끼리 꼬여서!
        productOptionService.deleteByProductId(product.getId());
        sellingRepository.deleteBySproduct(product);
        buyingRepository.deleteByProductOptionId(id);
        productRepository.delete(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Pcategory> findAllCategories() {
        return pcategoryRepository.findAll();
    }

    @Override
    public Map<Long, List<Product>> groupProductsByCategory() {
        List<Pcategory> categories = pcategoryRepository.findAll();
        Map<Long, List<Product>> productsByCategory = new HashMap<>();

        for (Pcategory category : categories) {
            List<Product> products = productRepository.findByPcategory(category);
            productsByCategory.put(category.getId(), products);
        }

        return productsByCategory;
    }


    @Override
    @Transactional
    public List<Product> search(String keyword) {
        return productRepository.findByPnameContaining(keyword);
    }

    @Override
    public void updateFormattedPrice(Long id, String formattedPrice) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setFormattedPrice(formattedPrice);

        productRepository.save(product);
    }

    // 해당 상품의 모든 옵션 가져와
    public List<ProductOption> getProductOptions(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }

}