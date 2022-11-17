package com.gwnu.dongdongju.api.service;

import com.google.gson.JsonObject;
import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Review;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.ReviewRepository;
import com.gwnu.dongdongju.api.repository.StoreInfoRepository;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import com.gwnu.dongdongju.api.service.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UsersRepository usersRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final Response response;

    @Autowired
    private S3Uploader s3Uploader;


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

//    //리뷰 이미지 업로드//
//    @Transactional
//    public ResponseEntity<?> reviewImageUpload(MultipartFile image, Long id) //, HttpServletRequest request
//    throws IOException {
//
//        Review reviews = reviewRepository.findById(id).orElse(null);
//        if (reviews == null){
//            response.fail("해당 리뷰가 존재하지 않습니다." + id, HttpStatus.NOT_FOUND);
//        }
//
//        if(!image.isEmpty()) {
//            String storedFileName = s3Uploader.upload(image,"images");
//            reviews.updateReviewImg(storedFileName);
//        }
//        return response.success("리뷰 이미지 변경에 성공했습니다.");
//    }
//
//    //체크섬 리뷰 수정//
//    @Transactional
//    public ResponseEntity<?> updateReview(Review review, Long reviewId){
//        Review reviews = reviewRepository.findById(reviewId).orElse(null);
//        if (reviews == null){
//            response.fail("해당 리뷰가 존재하지 않습니다." + reviewId, HttpStatus.NOT_FOUND);
//        }
//
//        reviews.update(review.getTitle(), review.getContent());
//
//        return response.success("리뷰가 수정되었습니다.");
//    }


    //리뷰 삭제//
    @Transactional
    public ResponseEntity<?> deleteReview(Long id){

        Review reviews = reviewRepository.findById(id).orElse(null);
        if (reviews == null){
            response.fail("해당 리뷰가 존재하지 않습니다." + id, HttpStatus.NOT_FOUND);
        }
        reviewRepository.delete(reviews);
        return response.success("리뷰가 삭제되었습니다.");
    }

    public ResponseEntity<?> createBoard(Review review, Long userId){

        JsonObject isChecked = new JsonObject();



        Users user = usersRepository.findById(userId).orElse(null);
        if (user == null) {
            response.fail("해당 유저가 존재하지 않습니다." + userId, HttpStatus.NOT_FOUND);
        }

//        IntStream.rangeClosed(1, 200).forEach(index ->
        Review save1 = reviewRepository.save(review.builder()
                .placeId(review.getPlaceId())
                .reviewId(review.getReviewId())
                .starPoint(review.getStarPoint())
                .content(review.getContent())
                .date(LocalDateTime.now())
                .email(user.getEmail())

//                .isChecked(tableRepository.save(TableStatus.builder()
//                        .revisit()
//                        .taste(review.isTaste())
//                        .nTaste(review.isNTaste())
//                        .service(review.isService())
//                        .nService(review.isNService())
//                        .hygiene(review.isHygiene())
//                        .nHygiene(review.isNHygiene())))

                .revisit(review.isRevisit())
                .taste(review.isTaste())
                .nTaste(review.isNTaste())
                .service(review.isService())
                .nService(review.isNService())
                .hygiene(review.isHygiene())
                .nHygiene(review.isNHygiene())

                .build());


        return response.success(save1,"리뷰작성에 성공했습니다.", HttpStatus.OK);
    }


}
