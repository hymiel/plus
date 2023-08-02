package com.sparta.plus.dto;

import com.sparta.plus.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String author;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        this.id = post.getPostId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.author = post.getUser().getNickName();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }


}
