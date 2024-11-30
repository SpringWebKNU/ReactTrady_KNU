package com.example.trady.service;

import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Member;
import com.example.trady.entity.Qna;
import com.example.trady.repository.MemberRepository;
import com.example.trady.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaServiceImpl implements QnaService {

    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public QnaServiceImpl(QnaRepository qnaRepository, MemberRepository memberRepository) {
        this.qnaRepository = qnaRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Qna> getAllQnas() {
        return qnaRepository.findAll();
    }

    @Override
    public Qna createQna(QnaForm qnaForm) {
        // memberId가 유효한지 먼저 확인
        if (qnaForm.getMemberId() == null || qnaForm.getMemberId() <= 0) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        // Member 조회
        Member member = memberRepository.findById(qnaForm.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Qna 엔티티 생성
        Qna qna = new Qna(qnaForm.getTitle(), qnaForm.getContent(), member);
        return qnaRepository.save(qna);
    }

}
