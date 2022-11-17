package com.gwnu.dongdongju.api.service;

import com.gwnu.dongdongju.api.dto.Response;
import com.gwnu.dongdongju.api.entity.Users;
import com.gwnu.dongdongju.api.repository.UsersRepository;
import com.gwnu.dongdongju.api.service.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoService {

    private final UsersRepository usersRepository;
    private final Response response;

    @Autowired private S3Uploader s3Uploader;


    //프로필 이미지 업로드//
    @Transactional
    public ResponseEntity<?> profileImageUpload(MultipartFile image, Long id) //, HttpServletRequest request
            throws IOException {
                Optional<Users> findUser = usersRepository.findById(id);

                if(!image.isEmpty()) {
            String storedFileName = s3Uploader.upload(image,"images");
            findUser.get().updateProfileImg(storedFileName);
        }
        return response.success("프로필 이미지 변경에 성공했습니다.");
    }



}

