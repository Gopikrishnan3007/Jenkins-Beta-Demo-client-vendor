//package com.rts.tap.config;
//
//import org.apache.hc.client5.http.impl.cookie.BasicMaxAgeHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//	@Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/tap/**")
//                .allowedOrigins("http://localhost:3000")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
//                .allowedHeaders("Authorization", "Content-Type", "Accept")
//                .exposedHeaders("Authorization")
//                .allowCredentials(true);
//    }
//	
//}
