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

    // GET 요청을 보내는 경우
    @GetMapping("/products/{id}/sell")
    public String showSellPage(@PathVariable("id") Long productId, Model model) {
        // 1. 상품 정보 조회
        Product product = productService.findProductById(productId);

        // 2. 해당 상품에 대한 모든 옵션 조회
        List<ProductOption> productOptions = productOptionService.findByProduct(product);

        // 3. 모델에 상품 정보와 옵션 리스트를 추가
        model.addAttribute("product", product);
        model.addAttribute("productOptions", productOptions);  // 사이즈 옵션들 전달
        model.addAttribute("productId", productId);  // productId 추가

        // 4. 판매 폼 페이지 반환
        return "products/sell";  // sellForm.mustache로 렌더링됩니다.
    }

    // 상품 판매 폼 처리
    @PostMapping("/products/{id}/sell")
    public String sellProduct(@PathVariable("id") Long productId,
                              @ModelAttribute SellingForm sellingForm,
                              HttpSession session,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        // 1. 상품 정보 조회
        Product product = productService.findProductById(productId);

        // 2. 세션에서 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("currentUser");
        if (member == null) {
            log.error("로그인된 사용자가 없습니다.");
            redirectAttributes.addFlashAttribute("msg", "로그인된 사용자가 없습니다. 로그인 해주세요.");
            return "redirect:/members/login";  // 로그인 페이지로 리다이렉트
        }

        // 판매 가격 검증 (백만원 초과 금지)
        long price = sellingForm.getSprice();
        if (price > 1000000) {
            // 가격 초과 오류 메시지 모델에 추가
            model.addAttribute("error", "가격은 1,000,000원 이하로 설정해야 합니다.");
            model.addAttribute("product", product);  // 상품 정보도 모델에 추가
            return "products/sell";  // 판매 등록 페이지로 다시 리턴
        }

        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedPrice = formatter.format(price); // 포맷팅된 가격

        // 포맷된 가격을 모델에 추가
        model.addAttribute("formattedPrice", formattedPrice);

        // 3. SellingForm 데이터를 이용해 Selling 엔티티 생성
        Selling selling = sellingForm.toEntity();
        selling.setSproduct(product);  // 상품 정보 설정
        selling.setUser(member);  // 로그인한 사용자 설정

        // 로그를 추가하여 값 확인
        log.info("Selling 정보: {}", selling);
        log.info("로그인한 사용자: {}", member);

        // 4. 판매 정보 저장 및 상품 옵션 생성
        sellingService.createSelling(selling, product, sellingForm.getSize(), sellingForm.getSprice());
        redirectAttributes.addFlashAttribute("msg", "상품 등록이 되었습니다.");
        // 5. 상품 상세 페이지로 리다이렉트
        return "redirect:/products/" + productId;
    }

    @PostMapping("/selling/{sellingId}/delete")
    public String deleteSelling(@PathVariable("sellingId") Long sellingId, HttpSession session, Model model) {
        // 세션에서 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("currentUser");

        // 관리자 여부 확인 (username이 "admin"인지 검사)
        Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");


        try {
            // 판매 정보 삭제
            sellingService.deleteSelling(sellingId);

            if (isAdmin) {
                // 관리자인 경우 관리자 페이지로 이동
                return "redirect:/members/admin";
            }

            // 일반 사용자: 삭제 성공 메시지 추가
            model.addAttribute("successMessage", "상품이 삭제되었습니다.");
        } catch (Exception e) {
            log.error("상품 삭제 중 오류 발생: {}", e.getMessage());
            model.addAttribute("error", "삭제 중 오류가 발생했습니다. 다시 시도해 주세요.");
        }

        // 일반 사용자는 마이페이지로 이동
        return "redirect:/members/mypage";
    }

}
