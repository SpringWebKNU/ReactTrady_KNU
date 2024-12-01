package com.example.trady.controller;

import com.example.trady.dto.MemberForm;
import com.example.trady.entity.Buying;
import com.example.trady.entity.Member;
import com.example.trady.entity.Selling;
import com.example.trady.service.BuyingService;
import com.example.trady.service.MemberService;
import com.example.trady.service.SellingService;
import jakarta.servlet.http.HttpSession;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    @Autowired
    private MemberService memberService;

    @Autowired
    private SellingService sellingService;

    @Autowired
    private BuyingService buyingService;


    public MemberController() {
    }

    @GetMapping({"/"})
    public String home(HttpSession session, Model model) {
        Member currentUser = (Member)session.getAttribute("currentUser");
        boolean isLoggedIn = currentUser != null;
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        session.setAttribute("isAdmin", isAdmin);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("currentUser", currentUser);
        return "index";
    }


    @GetMapping({"/members/admin"})
    public String adminPage(HttpSession session, Model model) {
        if (this.isAdmin(session)) {
            Iterable<Member> allMembers = this.memberService.getAllUsers();
            model.addAttribute("members", allMembers);
            return "members/admin";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping({"/members/all"})
    public String showAllMembers(HttpSession session, Model model) {
        if (this.isAdmin(session)) {
            Iterable<Member> allMembers = this.memberService.getAllUsers();
            model.addAttribute("members", allMembers);
            return "members/admin";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping({"/members/login"})
    public String memberLoginForm() {
        return "members/login";
    }

    @PostMapping({"/members/login"})
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (this.memberService.login(username, password, session)) {
            redirectAttributes.addFlashAttribute("msg", "로그인 성공!");
            return "redirect:/products/all";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "members/login";
        }
    }

    @PostMapping({"/members/logout"})
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        this.memberService.logout(session);
        redirectAttributes.addFlashAttribute("msg", "로그아웃 성공!");
        return "redirect:/products/all";
    }

    @GetMapping({"/members/join"})
    public String newMemberJoinForm() {
        return "members/join";
    }

    @PostMapping({"/members/create"})
    public String createUser(MemberForm memberForm, Model model) {
        Member saved = this.memberService.createUser(memberForm);
        model.addAttribute("username", saved.getUsername());
        return "members/welcome";
    }

    @GetMapping({"/members/mypage"})
    public String myPage(HttpSession session, Model model) {
        Member currentUser = (Member)session.getAttribute("currentUser");
        boolean isLoggedIn = currentUser != null;

        if (isLoggedIn) {
            model.addAttribute("currentUserModel", currentUser);
        }

        List<Selling> sellingList = sellingService.findAllByUser(currentUser);

        List<Buying> buyingList = buyingService.findAllByUser(currentUser);


        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("currentUserModel", currentUser);
        model.addAttribute("sellingList", sellingList);
        model.addAttribute("buyingList", buyingList);

        return "members/mypage";
    }

    @GetMapping({"/members/{userid}"})
    public String showUser(@PathVariable("userid") Long userid, Model model) {
        Member member = this.memberService.getUserById(userid);
        model.addAttribute("member", member);
        return "members/show";
    }


    @GetMapping("/usersell/{userid}")
    public String showUserSellingList(@PathVariable("userid") Long userid, Model model) {
        Member member = this.memberService.getUserById(userid);

        List<Selling> sellingList = this.sellingService.findAllByUser(member);
        model.addAttribute("member", member);
        model.addAttribute("sellingList", sellingList);

        return "members/usersell"; // 적절한 뷰 이름으로 수정
    }



    @GetMapping({"/members/{userid}/edit"})
    public String editUser(@PathVariable("userid") Long userid, Model model) {
        Member member = this.memberService.getUserById(userid);
        model.addAttribute("member", member);
        return "members/edit";
    }

    @GetMapping({"/members/{userid}/delete"})
    public String deleteUser(@PathVariable("userid") Long userid, HttpSession session, RedirectAttributes rttr) {
        Member currentUser = (Member)session.getAttribute("currentUser");
        Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
        if (currentUser != null && currentUser.getUserid().equals(userid)) {
            return this.handleUserDeletion(userid, session, rttr);
        } else if (Boolean.TRUE.equals(isAdmin)) {
            return this.handleUserDeletion(userid, session, rttr);
        } else {
            rttr.addFlashAttribute("msg", "권한이 없습니다.");
            return "redirect:/members/admin";
        }
    }

    private String handleUserDeletion(Long userid, HttpSession session, RedirectAttributes rttr) {
        boolean success = this.memberService.deleteUser(userid);

        if (success) {
            Member currentUser = (Member) session.getAttribute("currentUser");
            if (currentUser != null && currentUser.getUserid().equals(userid)) {
                // 현재 로그인한 유저와 삭제 대상 유저가 같으면 세션 종료
                session.invalidate();
                rttr.addFlashAttribute("msg", "계정이 삭제되었습니다. 로그아웃되었습니다.");
                return "redirect:/products/all";
            } else {
                // 관리자가 삭제한 경우
                rttr.addFlashAttribute("msg", "유저 계정이 삭제되었습니다.");
                return "redirect:/members/admin";
            }
        } else {
            rttr.addFlashAttribute("msg", "계정 삭제에 실패했습니다.");
            return "redirect:/members/admin";
        }
    }

    private boolean isAdmin(HttpSession session) {
        Boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
        return Boolean.TRUE.equals(isAdmin);
    }

    @PostMapping({"/members/update"})
    public String updateUser(MemberForm memberForm) {
        boolean success = this.memberService.updateUser(memberForm);
        return success ? "redirect:/members/" + memberForm.getUserid() : "redirect:/members/mypage";
    }

    @GetMapping("/api/session")
    @ResponseBody
    public Map<String, Object> getSession(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member currentUser = (Member) session.getAttribute("currentUser");
        response.put("currentUser", currentUser);

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        response.put("isAdmin", isAdmin);

        return response;
    }


}