package com.gwnu.dongdongju;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableBatchProcessing
@SpringBootApplication
public class DongdongjuApplication {

	public static void main(String[] args) {
		SpringApplication.run(DongdongjuApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
////				registry.addMapping("/**").allowedOrigins("http://front-server.com");
//				registry.addMapping("/**").allowedOrigins("*");
//			}
//		};
//	}
//
}
