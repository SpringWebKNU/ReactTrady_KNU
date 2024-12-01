package com.example.trady.controller;

import com.example.trady.dto.QnaAnswerForm;
import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Member;
import com.example.trady.entity.Qna;
import com.example.trady.entity.QnaAnswer;
import com.example.trady.service.MemberService;
import com.example.trady.service.QnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qnas")
@CrossOrigin(origins = "http://localhost:3000")
public class QnaController {


    private final QnaService qnaService;
    private final MemberService memberService;  // MemberService 추가

    @Autowired
    public QnaController(QnaService qnaService, MemberService memberService) {
        this.qnaService = qnaService;
        this.memberService = memberService;  // 생성자에 의존성 주입
    }

    @GetMapping
    public ResponseEntity<List<Qna>> getAllQnas() {
        List<Qna> qnas = qnaService.getAllQnas();
        return ResponseEntity.ok(qnaService.getAllQnas());
    }

    @PostMapping
    public ResponseEntity<Qna> createQna(@RequestBody QnaForm qnaForm) {
        Qna qna = qnaService.createQna(qnaForm); // 로그인 없이 작성 가능
        return ResponseEntity.ok(qna);
    }

    // 특정 Q&A 조회
    @GetMapping("/{qnaId}")
    public ResponseEntity<Qna> getQnaById(@PathVariable Long qnaId) {
        Qna qna = qnaService.getQnaById(qnaId);
        return ResponseEntity.ok(qna);
    }

    // 특정 Q&A에 답변 추가
    // Q&A 답변 추가
//    @PostMapping("/{qnaId}/answers")
//    public ResponseEntity<QnaAnswer> addAnswer(
//            @PathVariable Long qnaId,
//            @RequestBody QnaAnswerForm qnaAnswerForm) {
//
//        // Member ID를 사용하여 Member 객체 조회
//        Member member = memberService.getUserById(qnaAnswerForm.getMemberId());
//
//        // Member가 존재하지 않으면 에러 처리
//        if (member == null) {
//            return ResponseEntity.badRequest().body(null);  // Member가 없으면 400 Bad Request 반환
//        }
//
//        // 답변 추가 서비스 호출
//        QnaAnswer qnaAnswer = qnaService.addAnswer(qnaId, qnaAnswerForm, member);  // 수정된 addAnswer 호출
//
//        // 성공적으로 작성된 답변을 반환
//        return ResponseEntity.ok(qnaAnswer);
//    }


    @GetMapping("/{qnaId}/answers")
    public ResponseEntity<List<QnaAnswer>> getAnswers(@PathVariable Long qnaId) {
        // 해당 QnaId에 대한 모든 답변을 조회하여 반환
        List<QnaAnswer> answers = qnaService.getAnswersByQnaId(qnaId);
        return ResponseEntity.ok(answers);
    }

    @PostMapping("/{qnaId}/answers")
    public ResponseEntity<QnaAnswer> addAnswer(
            @PathVariable Long qnaId,
            @RequestBody QnaAnswerForm qnaAnswerForm) {

        // Qna 객체 조회
        Qna qna = qnaService.getQnaById(qnaId);  // Qna 객체를 먼저 가져옵니다.

        // 답변을 추가하는 서비스 호출 (Member 없이 처리)
        QnaAnswer qnaAnswer = qnaService.addAnswerWithoutMember(qnaId, qnaAnswerForm);  // Member 없이 답변 추가

        // 성공적으로 작성된 답변을 반환
        return ResponseEntity.ok(qnaAnswer);
    }

    @PatchMapping("/{qnaId}")
    public ResponseEntity<Qna> updateQna(@PathVariable Long qnaId, @RequestBody QnaForm qnaForm) {
        // 해당 QnaId에 해당하는 게시글을 수정
        Qna updatedQna = qnaService.updateQna(qnaId, qnaForm);
        return ResponseEntity.ok(updatedQna);
    }


    @DeleteMapping("/{qnaId}")
    public ResponseEntity<Void> deleteQna(@PathVariable Long qnaId) {
        qnaService.deleteQna(qnaId);  // 서비스 레벨에서 삭제 처리
        return ResponseEntity.noContent().build();  // 삭제 완료 후 응답
    }


    @DeleteMapping("/{qnaId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long qnaId,
            @PathVariable Long answerId) {

        qnaService.deleteAnswer(qnaId, answerId);  // 서비스 레벨에서 삭제 처리
        return ResponseEntity.noContent().build();  // 삭제 완료 후 응답
    }



}
