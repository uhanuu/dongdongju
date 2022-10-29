package com.gwnu.dongdongju.api.controller;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Comment;
import com.gwnu.dongdongju.api.entity.Review;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.lib.Helper;
import com.gwnu.dongdongju.api.service.CommentService;
import com.gwnu.dongdongju.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersCommController {

    private final ReviewService reviewService;
    private final CommentService commentService;
    private final Response response;

    //    @GetMapping({"", "/"})
//    public ResponseEntity<?> board(@RequestParam(value="idx", defaultValue = "0") Long idx,
//                                Model model) {
//        model.addAttribute("board", boardService.findBoardByIdx(idx));
//        return response.success();
//    }
    @PostMapping("/{userId}/stores/{storeId}/reviews")
    public ResponseEntity<?> createReview(@PathVariable Long userId,@PathVariable Long storeId, @RequestBody Review review, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return reviewService.createBoard(review, userId,storeId);
    }


    @GetMapping("/boards")
    public ResponseEntity<?> allReviewList(@PageableDefault Pageable pageable, Model model, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        Page<Review> reviewList = reviewService.findReviewList(pageable);
        reviewList.stream().forEach(e -> e.getContent());
        model.addAttribute("reviewList", reviewList);
        return response.success();
    }

    /**
     * Pageable은 페이지 요청을 받을 수 있는 인터페이스입니다. 보통 Pageable의 구현체인 PageRequest의
     * 인스턴스를 받아 이 안에 있는 데이터를 가지고 페이징 관련 처리를 하게 됩니다.Model은 View 층에서
     * JSP나 Thymeleaf와 같은 템플릿 엔진이 동적으로 HTML 페이지를 만드는 데 필요한 데이터를 제공해줍니다.
     */

    @PutMapping("/reviews/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id,@RequestBody Review review, Errors errors){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return reviewService.updateReview(review, id);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){

        return reviewService.deleteReview(id);
    }


    @PostMapping("/{userId}/reviews/{reviewId}/comments")
    public ResponseEntity<?> createComment (@PathVariable Long userId,@PathVariable Long reviewId, @RequestBody Comment comment, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return commentService.createComment(comment, userId, reviewId);
    }

    @PutMapping("/reviews/comment/{id}")
    public ResponseEntity<?> updateComment (@PathVariable Long id,@RequestBody Comment comment, Errors errors){
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return commentService.updateComment(comment, id);
    }

    @DeleteMapping("/reviews/comment/{id}")
    public ResponseEntity<?> deleteComment (@PathVariable Long id){

        return commentService.deleteComment(id);
    }

}
