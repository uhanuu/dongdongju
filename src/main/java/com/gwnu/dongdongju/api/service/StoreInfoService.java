package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Store;
import com.gwnu.dongdongju.api.repository.StoreInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreInfoService {

    private final StoreInfoRepository storeInfoRepository;
    private final Response response;

    public ResponseEntity<?> joinStore(Store store){
        if(storeInfoRepository.existsByName(store.getName())){
            return response.fail("이미 가입된 가게 입니다.", HttpStatus.BAD_REQUEST);
        }

        storeInfoRepository.save(Store.builder()
                .name(store.getName())
                .build());

        return response.success("가게 등록에 성공했습니다.");
    }
}
