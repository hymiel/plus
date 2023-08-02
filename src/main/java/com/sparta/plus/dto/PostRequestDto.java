package com.sparta.plus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String contents;

    private String author;

    public PostRequestDto(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }
}
