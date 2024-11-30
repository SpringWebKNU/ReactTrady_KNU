package com.example.trady.service;

import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Qna;

import java.util.List;

public interface QnaService {
    List<Qna> getAllQnas();

    Qna createQna(QnaForm qnaForm); // QnaForm을 매개변수로 받도록 수정
}
