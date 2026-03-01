package com.example.Bai2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map /images/** to the local uploads/ directory and the classpath static images folder
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:uploads/", "classpath:/static/images/");
    }
}
