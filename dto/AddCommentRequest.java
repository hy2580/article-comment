package com.example.blog.dto;

import com.example.blog.domain.Article;
import com.example.blog.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentRequest {
    private String body;

    public Comment toEntity(Article article){
        return Comment.builder()
                .body(body)
                .build();
    }

}
