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
        // 임시로 로그인된 사용자를 사용 (예시로 ID 1을 사용)
        Member member = memberRepository.findById(1L)  // 임의로 ID 1번 사용자 사용
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 기존 Qna 엔티티를 생성하지 않고, 그냥 값을 추가하는 방식으로 처리
        Qna qna = new Qna();  // 기존 엔티티에 값을 덮어씀
        qna.setTitle(qnaForm.getTitle());
        qna.setContent(qnaForm.getContent());
        qna.setMember(member);  // 작성자 정보를 설정

        return qnaRepository.save(qna);
    }

    @Override
    public Qna getQnaById(Long qnaId) {
        return qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("Qna not found"));
    }


    // Member 객체를 인자로 받도록 addAnswer 메서드 수정
    public QnaAnswer addAnswer(Long qnaId, QnaAnswerForm qnaAnswerForm, Member member) {
        // Qna ID로 Qna 객체를 찾음
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("Qna를 찾을 수 없습니다."));

        // QnaAnswer 객체 생성
        QnaAnswer qnaAnswer = new QnaAnswer(qnaAnswerForm.getContent(), qna, member);

        // QnaAnswer 저장
        return qnaAnswerRepository.save(qnaAnswer);
    }

    public QnaAnswer addAnswerWithoutMember(Long qnaId, QnaAnswerForm qnaAnswerForm) {
        // Qna 객체 조회
        Qna qna = getQnaById(qnaId);

        // QnaAnswer 객체 생성 (Member 없이)
        QnaAnswer qnaAnswer = new QnaAnswer();
        qnaAnswer.setContent(qnaAnswerForm.getContent());  // 답변 내용 설정
        qnaAnswer.setQna(qna);  // Qna와 연결

        // QnaAnswer 저장
        return qnaAnswerRepository.save(qnaAnswer);
    }


    @Override
    public List<QnaAnswer> getAnswersByQnaId(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("Qna not found"));

        // 해당 Qna에 대한 모든 답변을 반환
        return qnaAnswerRepository.findByQna(qna);
    }

    public void deleteQna(Long qnaId) {
        // 게시글 조회
        Qna qna = qnaRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("Qna not found"));

        // 해당 게시글에 연결된 답변 삭제
        List<QnaAnswer> answers = qnaAnswerRepository.findByQna(qna);
        for (QnaAnswer answer : answers) {
            qnaAnswerRepository.delete(answer);  // 답변 삭제
        }

        // 게시글 삭제
        qnaRepository.delete(qna);
    }



    public void deleteAnswer(Long qnaId, Long answerId) {
        // 답변 조회
        QnaAnswer qnaAnswer = qnaAnswerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        // 답변 삭제
        qnaAnswerRepository.delete(qnaAnswer);  // 답변 삭제
    }

}