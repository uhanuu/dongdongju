package com.gwnu.dongdongju.api.controller;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.dto.request.LoginDto;
import com.gwnu.dongdongju.api.dto.request.LogoutDto;
import com.gwnu.dongdongju.api.dto.request.ReissueDto;
import com.gwnu.dongdongju.api.dto.request.UserDto;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.jwt.JwtTokenProvider;
import com.gwnu.dongdongju.api.lib.Helper;
import com.gwnu.dongdongju.api.service.UserInfoService;
import com.gwnu.dongdongju.api.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UsersController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsersService usersService;
    private final UserInfoService userInfoService;
    private final Response response;


    @PostMapping("/users/sign-up")
    public ResponseEntity<?> signUp(@Validated @RequestBody UserDto userDto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.signUp(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDto loginDto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.login(loginDto);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated @RequestBody ReissueDto reissueDto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.reissue(reissueDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody LogoutDto logoutDto, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return usersService.logout(logoutDto);
    }

    @PatchMapping(value = "/users/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> imageUpload(
            @RequestParam ("image") MultipartFile image, Users user, HttpServletRequest request, Errors errors)
            throws IOException {
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
//        Long userId = user.getId();
        return userInfoService.profileImageUpload(image,user);
    }

    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return usersService.authority();
    }

    @GetMapping("/userTest")
    public ResponseEntity<?> userTest() {
        log.info("ROLE_USER TEST");
        return response.success();
    }

    @GetMapping("/adminTest")
    public ResponseEntity<?> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return response.success();
    }
}
