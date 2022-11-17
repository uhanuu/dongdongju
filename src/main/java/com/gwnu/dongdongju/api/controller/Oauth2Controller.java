package com.gwnu.dongdongju.api.controller;

import com.gwnu.dongdongju.api.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final KakaoService kakaoService;

    @RequestMapping("/kakao")
    public String home(@RequestParam(value = "code", required = false) String code) throws Exception {
        System.out.println("#########" + code);
        String access_Token = kakaoService.getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);

        // 위에서 만든 코드 아래에 코드 추가
        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###nickname#### : " + userInfo.get("nickname"));
        System.out.println("###profile_img#### : " + userInfo.get("profile_Image"));
        System.out.println("###email#### : " + userInfo.get("email"));


        return "testPage";
    }


}
