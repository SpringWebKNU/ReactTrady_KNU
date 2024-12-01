package com.example.trady.service;

import com.example.trady.dto.QnaAnswerForm;
import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Member;
import com.example.trady.entity.Qna;
import com.example.trady.entity.QnaAnswer;

import java.util.List;

public interface QnaService {
    List<Qna> getAllQnas();

    Qna createQna(QnaForm qnaForm); // QnaForm을 매개변수로 받도록 수정

    Qna getQnaById(Long qnaId);  // Fetches a Qna by ID

    QnaAnswer addAnswer(Long qnaId, QnaAnswerForm qnaAnswerForm, Member member);

    QnaAnswer addAnswerWithoutMember(Long qnaId, QnaAnswerForm qnaAnswerForm);

    List<QnaAnswer> getAnswersByQnaId(Long qnaId);

    void deleteQna(Long qnaId);

    void deleteAnswer(Long qnaId, Long answerId);
}