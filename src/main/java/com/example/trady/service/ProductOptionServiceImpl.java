package com.example.trady.service;

import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import com.example.trady.repository.ProductOptionRepository;
import com.example.trady.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductOptionServiceImpl implements ProductOptionService{
    private final ProductOptionRepository productOptionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    public ProductOptionServiceImpl(ProductOptionRepository productOptionRepository) {
        this.productOptionRepository = productOptionRepository;
    }

    @Override
    public List<ProductOption> findByProductId(Long productId) {
        return productOptionRepository.findByProductId(productId);
    }

    @Override
    public ProductOption save(ProductOption productOption) {
        return productOptionRepository.save(productOption);
    }

    @Override
    public void deleteByProductId(Long productId) {
        List<ProductOption> options = productOptionRepository.findByProductId(productId);
        for (ProductOption option : options) {
            productOptionRepository.delete(option);
        }
    }

    @Override
    public void createProductOption(Product product, String size, long price) {
        // ProductOption 생성 후 저장
        ProductOption productOption = new ProductOption();
        productOption.setProduct(product);
        productOption.setSize(size);
        productOption.setPrice(price);

        productOptionRepository.save(productOption);
    }


    @Override
    public List<ProductOption> findByProduct(Product product) {
        return productOptionRepository.findByProduct(product);
    }

    @Override
    public Long findLowestPriceByProductId(Long id) {
        return productOptionRepository.findLowestPriceByProductId(id);
    }

}
