//package com.ravi.semester.Config;
//import com.ravi.semester.Config.RequestInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    private final RequestInterceptor requestInterceptor;
//
//    public WebConfig(RequestInterceptor requestInterceptor) {
//        this.requestInterceptor = requestInterceptor;
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(requestInterceptor);
//    }
//}