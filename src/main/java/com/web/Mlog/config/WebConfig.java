package com.web.Mlog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String connectPath = "/files/**";
    private String resourcePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")) {
            resourcePath = "file:///C:/home/ubuntu/mlog/resource/";
        } else {
            resourcePath = "file:///home/ubuntu/mlog/resource/";
        }
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
    }
}
