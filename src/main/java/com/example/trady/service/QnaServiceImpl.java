package com.example.trady.service;

import com.example.trady.dto.QnaAnswerForm;
import com.example.trady.dto.QnaForm;
import com.example.trady.entity.Member;
import com.example.trady.entity.Qna;
import com.example.trady.entity.QnaAnswer;
import com.example.trady.repository.MemberRepository;
import com.example.trady.repository.QnaAnswerRepository;
import com.example.trady.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaServiceImpl implements QnaService {

    @Autowired
    private final QnaRepository qnaRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final QnaAnswerRepository qnaAnswerRepository;

    @Autowired
    public QnaServiceImpl(QnaRepository qnaRepository, MemberRepository memberRepository, QnaAnswerRepository qnaAnswerRepository) {
        this.qnaRepository = qnaRepository;
        this.memberRepository = memberRepository;
        this.qnaAnswerRepository = qnaAnswerRepository;
    }

    @Override
    public List<Qna> getAllQnas() {
        return qnaRepository.findAll();
    }

    @Override
    public Qna createQna(QnaForm qnaForm) {
        //리액트 연동시 세션 유지 x -> 유저 정보 고정해둠
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("xx"));

        Qna qna = new Qna();
        qna.setTitle(qnaForm.getTitle());
        qna.setContent(qnaForm.getContent());
        qna.setMember(member);

        return qnaRepository.save(qna);
    }

    @Override
    public Qna getQnaById(Long qnaId) {
        return qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("Qna not found"));
    }

    public QnaAnswer addAnswerWithoutMember(Long qnaId, QnaAnswerForm qnaAnswerForm) {
        Qna qna = getQnaById(qnaId);
        QnaAnswer qnaAnswer = new QnaAnswer();
        qnaAnswer.setContent(qnaAnswerForm.getContent()); 
        qnaAnswer.setQna(qna); 

        return qnaAnswerRepository.save(qnaAnswer);
    }

    @Override
    public List<QnaAnswer> getAnswersByQnaId(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("xx"));

        return qnaAnswerRepository.findByQna(qna);
    }

    public void deleteQna(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("xx"));

        // 해당 게시글 답변 삭제 후 삭제해야됨!!!
        List<QnaAnswer> answers = qnaAnswerRepository.findByQna(qna);
        for (QnaAnswer answer : answers) {
            qnaAnswerRepository.delete(answer);
        }

        qnaRepository.delete(qna);
    }

    public void deleteAnswer(Long qnaId, Long answerId) {
        QnaAnswer qnaAnswer = qnaAnswerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("xx"));

        qnaAnswerRepository.delete(qnaAnswer);
    }

}