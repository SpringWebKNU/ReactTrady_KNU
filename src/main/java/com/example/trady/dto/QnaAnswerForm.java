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

    private String content;
    private Long qnaId;
    private Long memberId;

    public Long getMemberId() {
        return memberId;
    }
}
