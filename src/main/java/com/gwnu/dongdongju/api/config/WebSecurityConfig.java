package com.gwnu.dongdongju.api.config;

import com.gwnu.dongdongju.api.jwt.JwtAuthenticationFilter;
import com.gwnu.dongdongju.api.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/users/signup").permitAll()
                .antMatchers("/api/login", "/api/authority", "/api/reissue", "/api/logout").permitAll()
                .antMatchers("/api/userTest").hasRole("USER")
                .antMatchers("/api/adminTest").hasRole("ADMIN")
                .antMatchers("/api/users/**").permitAll()
                .antMatchers("/api/users/imag").permitAll()

                .antMatchers(
                        // -- Static resources
                        "/css/**", "/images/**", "/js/**"
                        // -- Swagger UI v2
                        , "/v2/api-docs", "/swagger-resources/**"
                        , "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui/index.html"
                        // -- Swagger UI v3 (Open API)
                        , "/v3/api-docs/**", "/swagger-ui/**"
                )
                .permitAll()

                .and()
                // enable h2-console
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthentictaionFilter 전에 적용시킨다.

        return httpSecurity.build();
    }

    // 암호화에 필요한 PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error");
    }
}
