package com.example.trady.repository;

import com.example.trady.entity.Qna;
import com.example.trady.entity.QnaAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaAnswerRepository extends JpaRepository<QnaAnswer, Long> {
    List<QnaAnswer> findByQna(Qna qna);
}
