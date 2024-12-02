package com.example.trady.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class QnaForm {
    private String title;
    private String content;
    private Long memberId;
}
