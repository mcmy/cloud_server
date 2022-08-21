package com.nfcat.cloud.interceptor;

import com.alibaba.fastjson.JSON;
import com.nfcat.cloud.annotation.JSONBody;
import com.nfcat.cloud.annotation.JSONBodyExclude;
import com.nfcat.cloud.data.JsonResponse;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.AsyncHandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class ResponseFormatJsonInterceptor implements HandlerMethodReturnValueHandler, AsyncHandlerMethodReturnValueHandler {

    public final RedisUtil redisUtil;
    private boolean cache = false;
    private String field;
    private long t;

    @Override
    public boolean isAsyncReturnValue(Object returnValue, @NotNull MethodParameter returnType) {
        return supportsReturnType(returnType);
    }

    @Override
    public boolean supportsReturnType(@NotNull MethodParameter returnType) {
        //排除结果包含JSONBodyExclude注解的方法
        return (ObjectUtils.isNotEmpty(returnType.getDeclaringClass().getAnnotation(JSONBody.class))
                || ObjectUtils.isNotEmpty(returnType.getAnnotatedElement().getAnnotation(JSONBody.class)))
                && ObjectUtils.isEmpty(returnType.getAnnotatedElement().getAnnotation(JSONBodyExclude.class));
    }

    @Override
    public void handleReturnValue(Object returnValueObject, @NotNull MethodParameter returnType, @NotNull ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        assert request != null && response != null;
        response.setContentType("application/json;charset=utf-8");
        JsonResponse returnValue;
        if (returnValueObject instanceof JsonResponse jsonResponse) {
            returnValue = jsonResponse;
        } else {
            returnValue = ResultCode.format(ResultCode.SUCCESS, returnValueObject);
        }

        //获取方法（类）上的参数
        JSONBody jsonBody = returnType.getAnnotatedElement().getAnnotation(JSONBody.class);

        if (jsonBody != null && jsonBody.cache()) {
            if (returnValue.code == 200) cache = true;
            t = jsonBody.cacheTimeSeconds();
            Object o = request.getAttribute(ConstantData.CACHE_FIELD);
            if (o != null) field = String.valueOf(o);
        }

        if (jsonBody == null) {
            jsonBody = returnType.getDeclaringClass().getAnnotation(JSONBody.class);
        }
        if (jsonBody.serializerFeature().length > 0) {
            writeBody(response, JSON.toJSONString(returnValue, jsonBody.serializerFeature()));
            return;
        }
        writeBody(response, JSON.toJSONString(returnValue));
    }

    public void writeBody(@NotNull HttpServletResponse response, String s) throws Exception {
        response.getWriter().write(s);
        if (cache && field != null) {
            if (t == -1) redisUtil.set(field, s);
            if (t > 0) redisUtil.set(field, s, t);
        }
    }
}
