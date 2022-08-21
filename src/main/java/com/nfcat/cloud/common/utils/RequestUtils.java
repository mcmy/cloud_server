package com.nfcat.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.nfcat.cloud.enums.ConstantData;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class RequestUtils {
    public static JSONObject getRequestJson(HttpServletRequest request) {
        if (request.getAttribute(ConstantData.REQUEST_ATTR_MAP) instanceof JSONObject jsonObject) {
            return jsonObject;
        }
        final String contentType = request.getContentType();
        if (contentType == null) {
            return setRequestJson(request);
        } else if (contentType.contains("application/x-www-form-urlencoded")) {
            final Map<String, String[]> parameterMap = request.getParameterMap();
            JSONObject jsonObject = new JSONObject();
            parameterMap.forEach((key, value) -> jsonObject.put(key, value[value.length - 1]));
            return setRequestJson(request,jsonObject);
        } else if (contentType.contains("application/json")) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                request.getReader().lines().forEach(stringBuilder::append);
            } catch (IOException ignored) {
            }
            return setRequestJson(request,JSONObject.parseObject(stringBuilder.toString()));
        }
        return setRequestJson(request);
    }

    public static JSONObject setRequestJson(HttpServletRequest request,JSONObject jsonObject) {
        request.setAttribute(ConstantData.REQUEST_ATTR_MAP, jsonObject);
        return jsonObject;
    }

    public static JSONObject setRequestJson(HttpServletRequest request) {
        return setRequestJson(request,new JSONObject());
    }
}
