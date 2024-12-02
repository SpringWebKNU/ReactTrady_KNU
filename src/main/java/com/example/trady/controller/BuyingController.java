package com.example.trady.controller;

import com.example.trady.email.MailDto;
import com.example.trady.email.MailService;
import com.example.trady.entity.Buying;
import com.example.trady.entity.Member;
import com.example.trady.entity.Product;
import com.example.trady.entity.ProductOption;
import com.example.trady.service.BuyingService;
import com.example.trady.service.ProductOptionService;
import com.example.trady.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class BuyingController {

    @Autowired
    private BuyingService buyingService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductOptionService productOptionService;
    @Autowired
    private MailService mailService;


    @GetMapping("/products/{productId}/buy")
    public String showProductPage(@PathVariable("productId") Long productId, Model model) {
        // 상품 정보 조회
        Product product = productService.findProductById(productId);
        model.addAttribute("product", product);

        // 상품 옵션(사이즈와 가격 정보) 조회
        List<ProductOption> productOptions = productOptionService.findByProductId(productId);
        model.addAttribute("productOptions", productOptions);

        return "products/buy";
    }


    @PostMapping("/buying/create")
    public String createBuying(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "productOptionId") Long productOptionId,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            // 세션에서 로그인한 사용자 정보를 가져옴
            Member member = (Member) session.getAttribute("currentUser");
            if (member == null) {
                log.error("로그인된 사용자가 없습니다.");
                redirectAttributes.addFlashAttribute("msg", "로그인된 사용자가 없습니다. 로그인 해주세요.");
                return "redirect:/members/login";
            }

            // 구매 생성
            Buying buying = buyingService.createBuyingWithUser(productId, productOptionId, member);

            // 구매 정보 전달
            model.addAttribute("userName", member.getUsername());
            model.addAttribute("email", member.getEmail());
            model.addAttribute("address", member.getAddr());
            model.addAttribute("productName", buying.getProduct().getPname());  // 선택된 상품 이름
            model.addAttribute("size", buying.getProductOption().getSize());  // 선택된 사이즈
            model.addAttribute("price", buying.getProductOption().getPrice());  // 선택된 사이즈
            model.addAttribute("buying", buying);
            model.addAttribute("productImageUrl", buying.getProduct().getPimg());  // 상품 이미지 URL


            // 이메일 발송
            String userEmail = member.getEmail();
            String productName = buying.getProduct().getPname();
            String size = buying.getProductOption().getSize();
            String price = String.valueOf(buying.getProductOption().getPrice());
            String address = member.getAddr();

            MailDto mailDto = new MailDto();
            mailDto.setAddress(userEmail);
            mailDto.setTitle(productName);
            mailDto.setSize(size);
            mailDto.setPrice(price);
            mailDto.setEmail(address);

            mailService.mailSend(mailDto);

            return "buying/success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "buying/error";
        }
    }

    @GetMapping("/buying/success")
    public String orderSuccess(Model model) {
        return "/buying/success";
    }

}
