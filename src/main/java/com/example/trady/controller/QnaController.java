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

    // 리액트 연동 구현했습니다.

    private final QnaService qnaService;
    private final MemberService memberService;

    @Autowired
    public QnaController(QnaService qnaService, MemberService memberService) {
        this.qnaService = qnaService;
        this.memberService = memberService;
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


    @GetMapping("/{qnaId}/answers")
    public ResponseEntity<List<QnaAnswer>> getAnswers(@PathVariable Long qnaId) {
        List<QnaAnswer> answers = qnaService.getAnswersByQnaId(qnaId);
        return ResponseEntity.ok(answers);
    }

    @PostMapping("/{qnaId}/answers")
    public ResponseEntity<QnaAnswer> addAnswer(
            @PathVariable Long qnaId,
            @RequestBody QnaAnswerForm qnaAnswerForm) {

        Qna qna = qnaService.getQnaById(qnaId);  // Qna 먼저 가져옴
        QnaAnswer qnaAnswer = qnaService.addAnswerWithoutMember(qnaId, qnaAnswerForm);

        return ResponseEntity.ok(qnaAnswer);
    }

    @DeleteMapping("/{qnaId}")
    public ResponseEntity<Void> deleteQna(@PathVariable Long qnaId) {
        qnaService.deleteQna(qnaId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{qnaId}/answers/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable Long qnaId,
            @PathVariable Long answerId) {

        qnaService.deleteAnswer(qnaId, answerId);
        return ResponseEntity.noContent().build();
    }
}