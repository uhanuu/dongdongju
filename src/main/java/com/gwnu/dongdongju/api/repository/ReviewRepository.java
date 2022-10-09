package com.gwnu.dongdongju.api.repository;

import com.gwnu.dongdongju.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
