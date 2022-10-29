package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Comment;
import com.gwnu.dongdongju.api.entity.Review;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.CommentRepository;
import com.gwnu.dongdongju.api.repository.ReviewRepository;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    private final UsersRepository usersRepository;
    private final Response response;

    public ResponseEntity<?> createComment(Comment comment,Long userId ,Long reviewId){

        Review reviews = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰가 없습니다." + reviewId));
        Users users = usersRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("해당 유저가 없습니다." + userId));


        commentRepository.save(comment.builder()
                .content(comment.getContent())
                .review(reviews)
                .date(LocalDateTime.now())
                .user(users)
                .build()
        );

        return response.success("댓글작성에 성공했습니다.");
    }

    //댓글 수정//
    @Transactional
    public ResponseEntity<?> updateComment(Comment comment, Long commentId){
        Comment comments = commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 없습니다." + commentId));

        comments.update(comment.getContent());

        return response.success("댓글이 수정되었습니다.");
    }

    //댓글 삭제//
    @Transactional
    public ResponseEntity<?> deleteComment(Long id){
        Comment comments = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 없습니다." + id));

        commentRepository.delete(comments);
        return response.success("댓글이 삭제되었습니다.");
    }
}
