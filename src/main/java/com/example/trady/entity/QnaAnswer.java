package com.example.trady.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnaAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;  // 답변 내용

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();  // 답변 작성 시간

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)  // 질문과 연결
    private Qna qna;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;  // 관리자가 답변을 작성

    public QnaAnswer(String content, Qna qna, Member member) {
        this.content = content;
        this.qna = qna;
        this.member = member;
    }
}
