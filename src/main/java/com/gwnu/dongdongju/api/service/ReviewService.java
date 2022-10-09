package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Review;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.ReviewRepository;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final Response response;

    public Page<Review> findReviewList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());

        return reviewRepository.findAll(pageable);
    }

//    public ResponseEntity<?> findReviewById(Long userid) {
//        if (userid == null)
//        {
//            return response.fail("해당 유저가 없습니다.", HttpStatus.NOT_FOUND);
//        }
//        Review byUsers = reviewRepository.findAllById(userid);
//
//
//        return response.success("리뷰 조회를 성공했습니다.");
//    }

    public ResponseEntity<?> createBoard(Review review, Long userid){
        Optional<Users> user = usersRepository.findById(userid);
        System.out.println("user = " + user.get().getUsername());

//        IntStream.rangeClosed(1, 200).forEach(index ->
                reviewRepository.save(review.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .date(LocalDateTime.now())
                        .imgUrl(review.getImgUrl())
                        .user(user.get())
                        .build());

        return response.success("리뷰작성에 성공했습니다.");
    }

}
