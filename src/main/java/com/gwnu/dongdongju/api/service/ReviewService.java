package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Review;
import com.gwnu.dongdongju.api.entity.Store;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.ReviewRepository;
import com.gwnu.dongdongju.api.repository.StoreInfoRepository;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final StoreInfoRepository storeInfoRepository;
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

    //리뷰 수정//
    @Transactional
    public ResponseEntity<?> updateReview(Review review, Long reviewId){
        Review reviews = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰가 없습니다." + reviewId));

        reviews.update(review.getTitle(), review.getContent());

        return response.success("리뷰가 수정되었습니다.");
    }


    //리뷰 삭제//
    @Transactional
    public ResponseEntity<?> deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 리뷰가 없습니다." + id));

        reviewRepository.delete(review);
        return response.success("리뷰가 삭제되었습니다.");
    }

    public ResponseEntity<?> createBoard(Review review, Long userId,Long storeId){
        Users user = usersRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("해당 유저가 없습니다." + userId));
        Store store = storeInfoRepository.findById(storeId).orElseThrow(() ->
                new IllegalArgumentException("해당 가게가 없습니다." + storeId));

//        IntStream.rangeClosed(1, 200).forEach(index ->
                reviewRepository.save(review.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .date(LocalDateTime.now())
                        .imgUrl(review.getImgUrl())
                        .user(user)
                        .store(store)
                        .build());

        return response.success("리뷰작성에 성공했습니다.");
    }

}
