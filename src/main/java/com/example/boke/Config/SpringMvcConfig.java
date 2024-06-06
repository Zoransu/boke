package com.example.boke.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 放行哪些域名，可以多个
                .allowedOriginPatterns("*")
                // 是否发送Cookie信息
                .allowCredentials(true)
                // 允许的模式
                .allowedOriginPatterns("*")
                // 放行哪些请求方式
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                // 放行哪些原始域(头部信息)
                .allowedHeaders("*");
                // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//                .exposedHeaders("Header1", "Header2")
                // 预请求的结果有效期，默认1800分钟,3600是一小时
//                .maxAge(3600);
    }

}
