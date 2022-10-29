package com.gwnu.dongdongju.api.repository;


import com.gwnu.dongdongju.api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreInfoRepository extends JpaRepository<Store,Long> {
    boolean existsByName(String name);
}
