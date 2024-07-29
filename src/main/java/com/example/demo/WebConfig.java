package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/bk/**")
                // クッキーを共有したい他のサイトのURL
                        .allowedOrigins("http://localhost:3000",
                        		"https://bugknights-f.azurewebsites.net",
                        		"https://bugknights-b.azurewebsites.net")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true); // クッキーを許可する
            }
        };
    }
}
