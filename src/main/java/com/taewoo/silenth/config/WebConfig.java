package com.taewoo.silenth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 👇 이 부분을 아래와 같이 수정합니다.
        // 1. 경로의 마지막에 파일 구분자가 없으면 추가해줍니다.
        String resourcePath = uploadDir;
        if (!resourcePath.endsWith(File.separator)) {
            resourcePath += File.separator;
        }

        // 2. 어떤 OS에서도 인식할 수 있는 표준 파일 URL 형식(file:///)으로 만들어줍니다.
        // 윈도우의 경우 file:///C:/... 와 같은 형태가 됩니다.
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + resourcePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}