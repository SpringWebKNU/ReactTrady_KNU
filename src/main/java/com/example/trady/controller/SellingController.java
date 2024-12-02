package com.example.trady.controller;

import com.example.trady.dto.SellingForm;
import com.example.trady.entity.Member;
import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import com.example.trady.entity.Selling;
import com.example.trady.service.MemberService;
import com.example.trady.service.ProductOptionService;
import com.example.trady.service.ProductService;
import com.example.trady.service.SellingService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@Controller
public class SellingController {

    @Autowired
    private SellingService sellingService;
    
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionService productOptionService;
    
    @Autowired
    private MemberService memberService;  // MemberService 사용
    
    @GetMapping("/products/{id}/sell")
    public String showSellPage(@PathVariable("id") Long productId, Model model) {
      
        Product product = productService.findProductById(productId);
        List<ProductOption> productOptions = productOptionService.findByProduct(product);
        
        model.addAttribute("product", product);
        model.addAttribute("productOptions", productOptions);  
        model.addAttribute("productId", productId);
        
        return "products/sell"; 
    }

    // 상품 판매
    @PostMapping("/products/{id}/sell")
    public String sellProduct(@PathVariable("id") Long productId,
                              @ModelAttribute SellingForm sellingForm,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        Product product = productService.findProductById(productId);
        
        Member member = (Member) session.getAttribute("currentUser");
        if (member == null) {
            log.error("로그인된 사용자가 없습니다.");
            redirectAttributes.addFlashAttribute("msg", "로그인된 사용자가 없습니다. 로그인 해주세요.");
            return "redirect:/members/login";
        }

        // 백만원 초과 안됌!!!
        long price = sellingForm.getSprice();
        if (price > 1000000) {
            model.addAttribute("error", "가격은 1,000,000원 이하로 설정해야 합니다.");
            model.addAttribute("product", product); 
            return "products/sell";
        }

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedPrice = formatter.format(price); 
        
        model.addAttribute("formattedPrice", formattedPrice);
        
        Selling selling = sellingForm.toEntity();
        selling.setSproduct(product);  
        selling.setUser(member); 
        
        //log.info("Selling: {}", selling);
        //log.info("로그인: {}", member);
        
        sellingService.createSelling(selling, product, sellingForm.getSize(), sellingForm.getSprice());
        redirectAttributes.addFlashAttribute("msg", "상품 등록이 되었습니다.");
        return "redirect:/products/" + productId;
    }

    @PostMapping("/selling/{sellingId}/delete")
    public String deleteSelling(@PathVariable("sellingId") Long sellingId, HttpSession session, Model model) {
       
        Member member = (Member) session.getAttribute("currentUser");
        Boolean isAdmin = (Boolean)session.getAttribute("isAdmin"); // 관리자 여부

        try {
            // 판매 정보 삭제
            sellingService.deleteSelling(sellingId);
            if (isAdmin) {
                return "redirect:/members/admin";
            }

            model.addAttribute("successMessage", "상품이 삭제되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }

        return "redirect:/members/mypage";
    }

}
