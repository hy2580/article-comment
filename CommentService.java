package com.example.blog.service;

import com.example.blog.domain.Article;
import com.example.blog.domain.Comment;
import com.example.blog.dto.AddCommentRequest;
import com.example.blog.dto.UpdateCommentRequest;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    CommentRepository commentRepository;
    BlogRepository blogRepository;

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }


    public Comment saveComment(Long id,AddCommentRequest request){
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no article found"));

        Comment comment = new Comment();
        comment.setBody(request.getBody());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setArticle(article);

        return commentRepository.save(comment);
    }
    public Comment findComment(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not exists id: " + id));
    }
    public void deleteComment(Long id){commentRepository.deleteById(id);}

    @Transactional
    public Comment updateComment(Long id, UpdateCommentRequest request){
        Comment comment=commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not exists id: " + id));
        comment.update(request.getBody());
        return comment;
    }

    public List<Comment> findComments(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }
}
