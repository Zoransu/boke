package com.example.boke.Filter;

import com.alibaba.fastjson.JSONObject;
import com.example.boke.utils.JwtUtils;
import com.example.boke.utils.Result;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    @Override//每次拦截都会用到
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String url = request.getRequestURL().toString();
        if(url.contains("login")||url.contains("register")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String jwt = request.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            Result err = Result.error("未登录");
            String json = JSONObject.toJSONString(err);
            response.getWriter().write(json);
            return;
        }
        try {
            JwtUtils.extractAllClaims(jwt);
            Long userId = JwtUtils.extractUserId(jwt);
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            writeErrorResponse(response, "JWT验证失败: " + e.getMessage());
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }


    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        Result errorResult = Result.error(message);
        String jsonResponse = JSONObject.toJSONString(errorResult);
        response.getWriter().write(jsonResponse);
    }
}
