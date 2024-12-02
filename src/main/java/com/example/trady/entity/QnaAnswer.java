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
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "qna_id", nullable = false)
    private Qna qna;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = true)
    private Member member;

    public QnaAnswer(String content, Qna qna, Member member) {
        this.content = content;
        this.qna = qna;
        this.member = member;
    }
}
