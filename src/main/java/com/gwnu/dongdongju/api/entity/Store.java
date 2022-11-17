package com.gwnu.dongdongju.api.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String ImgUrl;

//    @OneToMany(mappedBy = "store")
//    private List<Review> reviewList;

//    @Column(e)
////    @ElementCollection(fetch = FetchType.EAGER)
////    @Builder.Default
//    private List<String> roles = new ArrayList<>();
}
