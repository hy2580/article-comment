package com.example.blog.domain;

import ch.qos.logback.core.joran.action.Action;
import com.example.blog.dto.ArticleResponse;
import com.example.blog.dto.CommentResponse;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id",updatable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id",updatable=false)
    private Article article;

    @Column
    private String body;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Comment(String body) {
        this.body = body;
    }

    public CommentResponse toDto(){
        return new CommentResponse(id,body,createdAt, ArticleResponse.toDto(article));
    }

    @Transactional
    public void update(String body){
        this.body = body;
    }

}
