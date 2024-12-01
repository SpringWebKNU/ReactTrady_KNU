package com.example.trady.controller;

import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Qna;
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

    @GetMapping("/qnas")
    public String qnaPage() {
        return "index";  // index.mustache 템플릿을 반환
    }

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping
    public ResponseEntity<List<Qna>> getAllQnas() {
        List<Qna> qnas = qnaService.getAllQnas();
        return ResponseEntity.ok(qnaService.getAllQnas());
    }

    @PostMapping
    public ResponseEntity<Qna> createQna(@RequestBody QnaForm qnaForm) {
        Qna qna = qnaService.createQna(qnaForm);
        return ResponseEntity.ok(qna);
    }
}
