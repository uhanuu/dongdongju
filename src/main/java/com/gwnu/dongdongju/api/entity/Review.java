package com.gwnu.dongdongju.api.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime date;

    @Column
    private Long viewCount;

    @Column
    private String imgUrl;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments;

//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "review")
//    private List<Comment> comments;

    public void addViewCount() {
        this.viewCount++;
    }

//    public void addCommentCount(int num) {
//        if (this.numOfComment + num > 0)
//            this.numOfComment += num;
//        else
//            this.numOfComment = 0;
//    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void updateProfileImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
