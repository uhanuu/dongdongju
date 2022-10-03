package com.gwnu.dongdongju.api.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //OAuth2 userRequest 후 데이터 처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("userRequest:"+userRequest);
        super.loadUser(userRequest).getAttributes();
        userRequest.getClientRegistration(); // 어떤 OAuth로 로그인 했는지
        return super.loadUser(userRequest);
    }
}
