package com.example.trady.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaAnswerForm {

    private String content;  // 답변 내용
    private Long qnaId;      // 해당 Q&A ID
    private Long memberId;   // 답변을 작성한 관리자 ID

    public Long getMemberId() {
        return memberId;
    }
}
