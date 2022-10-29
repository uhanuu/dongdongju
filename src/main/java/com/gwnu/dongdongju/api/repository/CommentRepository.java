package com.gwnu.dongdongju.api.repository;

import com.gwnu.dongdongju.api.entity.Comment;
import com.gwnu.dongdongju.api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
