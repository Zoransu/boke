package com.example.boke.Filter;

import com.alibaba.fastjson.JSONObject;
import com.example.boke.utils.JwtUtils;
import com.example.boke.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@WebFilter(urlPatterns = "/*")
@Order(2)
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURL().toString();

        // 处理OPTIONS请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 放行登录、注册以及Swagger相关资源
        if (url.contains("getLastTen")||url.contains("getHotTen")||url.contains("login") || url.contains("register") ||
                url.matches("(?i).*(css|jpg|png|gif|js|swagger-ui.html|swagger-resources|v2/api-docs|v3/api-docs|swagger-ui/index.html|webjars|doc.html|favicon.ico).*")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String jwt = request.getHeader("token");
        if (!StringUtils.hasLength(jwt)) {
            writeErrorResponse(response, "未登录", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            JwtUtils.extractAllClaims(jwt);
            Long userId = JwtUtils.extractUserId(jwt);
            request.setAttribute("userId", userId);
            log.info("JWT验证成功，用户ID: {}", userId);
        } catch (Exception e) {
            writeErrorResponse(response, "JWT验证失败: " + e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void writeErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        Result errorResult = Result.error(message);
        String jsonResponse = JSONObject.toJSONString(errorResult);
        response.getWriter().write(jsonResponse);
    }
}
