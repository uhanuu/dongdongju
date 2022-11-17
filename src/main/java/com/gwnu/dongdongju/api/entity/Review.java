package com.gwnu.dongdongju.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "place_review")
public class Review {

    /**
     * placeId: string, // 카카오에서 제공함 의문 ㄴㄴ
     * starPoint: number, // 평점
     * content: string, // 내용
     * isChecked: {
     * revisit: boolean, // 재방문의사
     * taste: boolean, // 맛(만족)
     * nTaste: boolean, // 맛(불만족)
     * service: boolean, // 서비스(만족)
     * nService: boolean, // 서비스(불만족)
     * hygiene: boolean, // 위생(만족)
     * nHygiene: boolean, // 위생(불만족)
     * },
     * email: string, // 사용자를 구분하기 위한 key,
     * ㅁ						 // token으로 할지 email로 할지 고민
     * reviewId: string, // 내가 만들어서 보냄
     */
    @Id
    @Column(name = "place_id")
    private String placeId; // 카카오에서 제공

    private String reviewId; // 형이 만들어서 보냄

    private int starPoint;
    private String content;


    private boolean revisit; // 재방문의사
    private boolean taste; // 맛(만족)
    private boolean nTaste; // 맛(불만족)
    private boolean service; // 서비스(만족)
    private boolean nService; // 서비스(불만족)
    private boolean hygiene; // 위생(만족)
    private boolean nHygiene; // 위생(불만족)


    @Column
    private String email;

    @Column
    private LocalDateTime date;

//
//    @JoinColumn(name = "store_id")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Store store;
//
//    @OneToMany(mappedBy = "review")
//    private List<Comment> comments;
//
//    @OneToMany(mappedBy = "review")
//    private List<Likes> likesList;
//
//    @Column
//    private Long likes;


//    public void update(String title, String content){
//        this.title = title;
//        this.content = content;
//    }
//    public void updateReviewImg(String imgUrl) {
//        this.imgUrl = imgUrl;
//    }
//
//    //좋아요 개수
//    public void addLike(){
//        this.likes++;
//    }
//    //종아요 취소
//    public void cancelLike(){
//        this.likes--;
//    }
//
//    //조회 수 증가
//    public void addViewCount(){
//        this.viewCount++;
//    }
//
}
