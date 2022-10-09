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

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "review")
    private List<Comment> comments;

    public void addViewCount() {
        this.viewCount++;
    }

//    public void addCommentCount(int num) {
//        if (this.numOfComment + num > 0)
//            this.numOfComment += num;
//        else
//            this.numOfComment = 0;
//    }
}
