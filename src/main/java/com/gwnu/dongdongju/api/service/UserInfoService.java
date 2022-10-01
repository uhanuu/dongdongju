package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UsersRepository usersRepository;
    private final Response response;
//    private final Users users;

    @Autowired private S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<?> profileImageUpload(MultipartFile image, Users user) //, HttpServletRequest request
            throws IOException {

        if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            user.setProfileImg(storedFileName);
//            users.addProfileImg(storedFileName);

        }
        usersRepository.save(user);
        return response.success("프로필 이미지 변경에 성공했습니다.");
    }



}

