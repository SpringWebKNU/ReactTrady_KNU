package com.example.trady.service;

import com.example.trady.entity.*;
import com.example.trady.repository.BuyingRepository;
import com.example.trady.repository.ProductOptionRepository;
import com.example.trady.repository.ProductRepository;
import com.example.trady.repository.SellingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuyingServiceImpl implements BuyingService {

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final BuyingRepository buyingRepository;
    private final SellingRepository sellingRepository;

    public BuyingServiceImpl(ProductRepository productRepository,
                             ProductOptionRepository productOptionRepository,
                             BuyingRepository buyingRepository, SellingRepository sellingRepository) {
        this.productRepository = productRepository;
        this.productOptionRepository = productOptionRepository;
        this.buyingRepository = buyingRepository;
        this.sellingRepository = sellingRepository;
    }

    @Transactional
    public Buying createBuyingWithUser(Long productId, Long productOptionId, Member user) throws Exception {

        // 상품, 상품 옵션 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("xxxxx"));

        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new Exception("xxxxx"));

        // Buying 생성 여기에 회원 정보도 넣어줍니당!!!
        Buying buying = new Buying();
        buying.setUser(user);  // 로그인한 사용자
        buying.setProduct(product);
        buying.setProductOption(productOption);
        buying.setSize(productOption.getSize());
        buying.setPrice(productOption.getPrice());

        Buying savedBuying = buyingRepository.save(buying);
        
        Selling selling = sellingRepository.findByProductAndSize(productId, productOption.getSize());
        if (selling != null && !selling.isSold()) {
            selling.markAsSold();  // 판매완료됨
            sellingRepository.save(selling);
        }
        
        if (!productOption.isSold()) {
            productOption.markAsSold();  // 판매완료됨
            productOptionRepository.markAsSold(productOptionId);
        }

        // BUYING에서 product_option_id -> 다른 값으로.. (NULL을 피하기 위해)
        buyingRepository.updateProductOptionToDefault(productOptionId);
        productOptionRepository.deleteById(productOptionId);  // ProductOption 삭제

        return savedBuying;
    }

    public List<Buying> getPurchasesByProduct(Long productId) {
        return buyingRepository.findByProductId(productId);
    }

    @Override
    public List<Buying> findAllByUser(Member user) {
        return buyingRepository.findByUser(user);
    }

}
