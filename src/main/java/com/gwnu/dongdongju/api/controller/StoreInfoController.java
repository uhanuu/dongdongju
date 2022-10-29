package com.gwnu.dongdongju.api.controller;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Store;
import com.gwnu.dongdongju.api.lib.Helper;
import com.gwnu.dongdongju.api.service.StoreInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreInfoController {

    private final StoreInfoService storeInfoService;
    private final Response response;

    @PostMapping("/stores/join")
    public ResponseEntity<?> joinStore(@Valid @RequestBody Store store, Errors errors) {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return storeInfoService.joinStore(store);
    }
}
