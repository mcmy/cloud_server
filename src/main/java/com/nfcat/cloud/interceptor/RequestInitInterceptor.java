package com.nfcat.cloud.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Service
@Slf4j
public class RequestInitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (handler instanceof HandlerMethod) {
            // 设置编码
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException ignored) {
            }
            response.setCharacterEncoding("UTF-8");
        }
        return true;
    }
}
