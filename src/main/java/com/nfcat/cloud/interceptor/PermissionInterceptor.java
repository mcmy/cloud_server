package com.nfcat.cloud.interceptor;

import com.nfcat.cloud.annotation.AutoAuthentication;
import com.nfcat.cloud.annotation.AutoGenToken;
import com.nfcat.cloud.common.utils.RedisUtil;
import com.nfcat.cloud.common.utils.TypeConversionTool;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import com.nfcat.cloud.exception.CloudException;
import com.nfcat.cloud.server.HttpToken;
import com.nfcat.cloud.sql.entity.NfUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionInterceptor implements HandlerInterceptor {

    public final RedisUtil redisUtil;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        //TODO 权限拦截
        if (handler instanceof HandlerMethod method) {
            //验证是否有token
            if (!method.hasMethodAnnotation(AutoGenToken.class)
                    && HttpToken.getRequestTokenString(request) == null) {
                throw new AssertException(ResultCode.NOT_TOKEN);
            }
            HttpToken httpToken = new HttpToken(redisUtil, request, response);
            request.setAttribute(ConstantData.HTTP_TOKEN, httpToken);
            //验证权限
            final AutoAuthentication methodAnnotation = method.getMethodAnnotation(AutoAuthentication.class);
            final AutoAuthentication an = methodAnnotation == null ?
                    method.getMethod().getDeclaringClass().getAnnotation(AutoAuthentication.class) : methodAnnotation;
            if (an == null) return true;
            switch (an.value()) {
                case VISITOR -> {
                    return true;
                }
                case USER -> {
                    if (!(httpToken.getAttribute(ConstantData.USER_SESSION_DATA) instanceof NfUser)) {
                        throw new AssertException(ResultCode.USER_NOT_LOGIN);
                    }
                }
                case ADMIN -> {
                    if (httpToken.getAttribute(ConstantData.USER_SESSION_DATA) instanceof NfUser nfUser
                            && TypeConversionTool.toInt(nfUser.getUserGroup().substring(0, 1), 0) > 7) {
                        return true;
                    }
                    throw new CloudException(ResultCode.USER_NO_PERMISSION);
                }
            }
        }
        return true;
    }
}