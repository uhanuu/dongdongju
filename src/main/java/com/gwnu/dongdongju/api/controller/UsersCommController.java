package com.gwnu.dongdongju.api.controller;

import com.google.gson.JsonObject;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    @PostMapping("/{userId}/reviews")
    public ResponseEntity<?> createReview(@PathVariable Long userId, @RequestBody Review review, Errors errors) {
        JsonObject isChecked = new JsonObject();
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return reviewService.createBoard(review, userId);
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
     * Pageable??? ????????? ????????? ?????? ??? ?????? ????????????????????????. ?????? Pageable??? ???????????? PageRequest???
     * ??????????????? ?????? ??? ?????? ?????? ???????????? ????????? ????????? ?????? ????????? ?????? ?????????.Model??? View ?????????
     * JSP??? Thymeleaf??? ?????? ????????? ????????? ???????????? HTML ???????????? ????????? ??? ????????? ???????????? ??????????????????.
     */

//    @PutMapping("/reviews/{id}")
//    public ResponseEntity<?> updateReview(@PathVariable Long id,@RequestBody Review review, Errors errors){
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
//        return reviewService.updateReview(review, id);
//    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){

        return reviewService.deleteReview(id);
    }

    // ??????????????? ?????? put ??????????????? ???????????? ????????? ???????????? ???????????? ???????????? patch ??????????????? ???????????????.
//    @PatchMapping(value = "/reviews/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> imageUpload(
//            @PathVariable Long id,
//            @RequestParam ("image") MultipartFile image, HttpServletRequest request, Errors errors)
//            throws IOException {
//        if (errors.hasErrors()) {
//            return response.invalidFields(Helper.refineErrors(errors));
//        }
//        return reviewService.reviewImageUpload(image,id);
//    }


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
