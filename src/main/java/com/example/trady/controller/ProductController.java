package com.example.trady.controller;

import com.example.trady.entity.*;
import com.example.trady.service.*;
import com.example.trady.dto.ProductForm;
import com.example.trady.repository.PcategoryRepository;
import com.example.trady.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PcategoryService pcategoryService;

    @Autowired
    ProductOptionService productOptionService;

    @Autowired
    SellingService sellingService;

    @Autowired
    BuyingService buyingService;

    @Autowired
    public ProductController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @Autowired
    private PcategoryRepository pcategoryRepository;


    @GetMapping("/products/new")
    public String newProductForm(){
        return "products/new";
    }

    @PostMapping("/products/create")
    public String createProduct(ProductForm productForm) {
        log.info("Received ProductForm: {}", productForm);
        Product savedProduct = productService.saveProduct(productForm);
        return "redirect:/products/" + savedProduct.getId();
    }

    @GetMapping("/products/all")
    public String all(Model model) {
        List<Product> products = productService.findAllProducts();

        // 각 제품에 대해 최저가 조회
        for (Product product : products) {
            // 최저가
            Long lowestPrice = productOptionService.findLowestPriceByProductId(product.getId());

            String formattedPrice = (lowestPrice != null)
                    ? new DecimalFormat("#,###").format(lowestPrice)
                    : "-";

            product.setFormattedPrice(formattedPrice);
        }
        model.addAttribute("products", products);
        return "products/all";
    }

    @GetMapping("/products/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        List<ProductOption> productOptions = productService.getProductOptions(product.getId());
        Long lowestPrice = productOptionService.findLowestPriceByProductId(id);

        String formattedPrice = (lowestPrice != null)
                ? new DecimalFormat("#,###").format(lowestPrice)
                : "-";


        product.setFormattedPrice(formattedPrice);
        productService.updateFormattedPrice(id, formattedPrice);

        // 구매 완료된 상품 조회
        List<Buying> allBuyings = buyingService.getPurchasesByProduct(id);

        for (ProductOption option : productOptions) {
            if (option.getPrice() != 0) {
                option.setFormattedPrice(new DecimalFormat("#,###").format(option.getPrice()));
            }
        }

        for (Buying buying : allBuyings) {
            if (buying.getPrice() != 0) {
                buying.setFormattedPrice(new DecimalFormat("#,###").format(buying.getPrice()));
            }
        }

        model.addAttribute("product", product);
        model.addAttribute("productOptions", productOptions);
        model.addAttribute("buyings", allBuyings);

        return "products/show";
    }

    @GetMapping("/products/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Product product = productService.findProductById(id);
        List<Pcategory> categories = productService.findAllCategories();

        // 카테고리 선택 여부를 설정
        categories.forEach(category -> {
            if (category.getId().equals(product.getPcategory().getId())) {
                switch (category.getId().intValue()) {
                    case 1:
                        model.addAttribute("isCategory1", true);
                        break;
                    case 2:
                        model.addAttribute("isCategory2", true);
                        break;
                    case 3:
                        model.addAttribute("isCategory3", true);
                        break;
                    case 4:
                        model.addAttribute("isCategory4", true);
                        break;
                    case 5:
                        model.addAttribute("isCategory5", true);
                        break;
                    default:
                        break;
                }
            }
        });

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "products/edit";
    }


    @PostMapping("/products/update")
    public String update(ProductForm productForm) {
        Product updatedProduct = productService.updateProduct(productForm);
        return "redirect:/products/" + updatedProduct.getId();
    }

    @GetMapping("/products/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes rttr) {
        productOptionService.deleteByProductId(id);
        productService.deleteProduct(id);
        rttr.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/products/all";
    }

    @GetMapping("/products/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        log.info("Searching for products with keyword: {}", keyword);

        List<Product> products = productService.search(keyword);

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);

        return "products/searchResults";
    }


    @GetMapping("/products")
    public String listProducts(Model model) {
        // 카테고리, 제품
        List<Pcategory> categories = productService.findAllCategories();
        Map<Long, List<Product>> productsByCategory = productService.groupProductsByCategory();

        // 활성
        if (!categories.isEmpty()) {
            categories.get(0).setFirst(true);
        }

        log.info("categories: {}", categories);
        log.info("productsByCategory: {}", productsByCategory);

        model.addAttribute("categories", categories);
        model.addAttribute("productsByCategory", productsByCategory);

        return "products/list";
    }

    @RequestMapping("/products/{productId}/buy")
    public String showProductPage(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);

        List<ProductOption> productOptions = productOptionService.findByProductId(productId);
        model.addAttribute("productOptions", productOptions);

        return "products/buy";
    }

}