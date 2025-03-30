package com.crypto.trading.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Разрешава CORS за всички източници и пътища
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3001") // или '*' за всички източници
                .allowedMethods("GET", "POST", "PUT", "DELETE") // разрешава HTTP методите
                .allowedHeaders("*") // разрешава всички хедъри
                .allowCredentials(true); // разрешава изпращането на бисквитки
    }
}
