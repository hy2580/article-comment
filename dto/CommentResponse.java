package com.example.blog.dto;

import com.example.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor

public class CommentResponse {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private ArticleResponse article;

    public CommentResponse(Long id, String body, LocalDateTime createdAt,
                           ArticleResponse articleResponse) {
        this.id=id;
        this.body=body;
        this.createdAt=createdAt;
        this.article=articleResponse;
    }


}
