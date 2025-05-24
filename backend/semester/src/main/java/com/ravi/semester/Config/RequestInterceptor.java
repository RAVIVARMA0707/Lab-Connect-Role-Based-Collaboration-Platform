//package com.ravi.semester.Config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class RequestInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String origin = request.getHeader("Origin");  // Frontend URL
//        String referer = request.getHeader("Referer"); // Previous page URL
//        String userAgent = request.getHeader("User-Agent"); // Browser/Client
//
//        System.out.println("Origin: " + origin);
//        System.out.println("Referer: " + referer);
//        System.out.println("User-Agent: " + userAgent);
//
//        return true;
//    }
//}
