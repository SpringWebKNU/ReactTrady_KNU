package com.example.trady.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS 설정: /api/** 경로에 대해 React 도메인 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // React 개발 서버 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*"); // 모든 헤더 허용
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 이미지 파일 경로 등록
        registry.addResourceHandler("/images/uploads/**")
                .addResourceLocations("file:/C:/Trady/src/main/resources/static/images/uploads/")  // 절대 경로 사용
                .setCacheControl(CacheControl.maxAge(0, TimeUnit.SECONDS).cachePublic());  // 캐시를 아예 사용하지 않음
    }
}
