package com.nfcat.cloud.interceptor;

import com.nfcat.cloud.annotation.AccessLimit;
import com.nfcat.cloud.common.utils.TypeConversionTool;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.CloudException;
import com.nfcat.cloud.service.RedisUtilService;
import com.nfcat.cloud.service.interfaces.AccessLimitVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccessLimitInterceptor implements HandlerInterceptor {
    public final RedisUtilService redisUtilService;
    public static final String accessLimitRedisPrefix = "nfcat:cloud:access_limit:";
    public static Map<Class<? extends AccessLimitVerificationService>, AccessLimitVerificationService> verificationServiceMap = new HashMap<>();

    public static AccessLimitVerificationService getVerificationService(@NotNull AccessLimit accessLimit) {
        final Class<? extends AccessLimitVerificationService> verifyClass = accessLimit.getAccessLimitVerifyClass();
        if (verificationServiceMap.containsKey(verifyClass)) {
            return verificationServiceMap.get(verifyClass);
        }
        try {
            AccessLimitVerificationService service = verifyClass.getDeclaredConstructor().newInstance();
            verificationServiceMap.put(verifyClass, service);
            return service;
        } catch (Exception e) {
            throw new CloudException(ResultCode.ERROR);
        }
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        if (handler instanceof HandlerMethod method) {
            final AccessLimit methodAnnotation = method.getMethodAnnotation(AccessLimit.class);
            final AccessLimit an = methodAnnotation == null ?
                    method.getMethod().getDeclaringClass().getAnnotation(AccessLimit.class) : methodAnnotation;
            if (an == null) return true;
            final AccessLimitVerificationService verificationService = getVerificationService(an);
            final String key = accessLimitRedisPrefix + verificationService.getClientID(request);
            int redisSeconds = TypeConversionTool.toInt(redisUtilService.get(key), -1);
            //第一次访问
            if (redisSeconds == -1) {
                redisUtilService.set(key, 1, an.seconds());
                return true;
            }
            //正常访问
            if (redisSeconds < an.maxCount()) {
                redisUtilService.redisTemplate.opsForValue().increment(key);
            }
            //频繁访问
            return verificationService.verify(request);
        }
        return true;
    }

}
