package com.example.blog.controller;

import com.example.blog.domain.Article;
import com.example.blog.domain.Comment;
import com.example.blog.dto.AddCommentRequest;
import com.example.blog.dto.ArticleWithCommentsResponse;
import com.example.blog.dto.CommentResponse;
import com.example.blog.dto.UpdateCommentRequest;
import com.example.blog.service.BlogService;
import com.example.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final BlogService blogService;

    CommentController(CommentService commentService, BlogService articleService) {
        this.commentService = commentService;
        this.blogService = articleService;
    }


    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> saveComment(@PathVariable Long articleId, @RequestBody AddCommentRequest request){
        Comment savedComment=commentService.saveComment(articleId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment.toDto());
    }

    // comment ID 단건 조회
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponse> findComment(@PathVariable("commentId") Long id){
        Comment comment = commentService.findComment(id);
        return ResponseEntity.ok(comment.toDto());
    }

    // update
    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("commentId") Long id,
                                                         @RequestBody UpdateCommentRequest request){
        Comment comment = commentService.updateComment(id, request);
        CommentResponse response=comment.toDto();
        return ResponseEntity.ok(response);
    }

    //단건 comment ID 선택 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    // 게시글과 댓글 한번에 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<ArticleWithCommentsResponse> getArticleWithComments(@PathVariable Long articleId) {
        Article article = blogService.findArticle(articleId); // 게시글 조회
        List<Comment> comments = commentService.findComments(articleId); // 해당 게시글의 댓글

        // Comment -> CommentResponse로 변환
        List<CommentResponse> commentResponses = comments.stream()
                .map(Comment::toDto)
                .collect(Collectors.toList());


        ArticleWithCommentsResponse response = new ArticleWithCommentsResponse(article, commentResponses);
        return ResponseEntity.ok(response);
    }




}
