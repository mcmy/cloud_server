package com.nfcat.cloud.server;

import com.alibaba.fastjson.JSONObject;
import com.nfcat.cloud.data.Token;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.utils.NanoIdUtils;
import com.nfcat.cloud.utils.RedisUtil;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpToken {

    private final RedisUtil redisUtil;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final Token token;
    private final String itemPrefix = "data-";
    private final String redisPrefix;

    private static final String tokenName = "X-TOKEN";
    private static final long maxSeconds = 86400;
    private static final long refreshSeconds = 7200;

    public HttpToken(RedisUtil redisUtil, HttpServletRequest request, HttpServletResponse response) {
        this.redisUtil = redisUtil;
        this.request = request;
        this.response = response;
        String tokenString = getTokenString();
        this.redisPrefix = "nfcat:cloud:token:" + tokenString;
        this.token = new Token().setToken(tokenString);
        if (!validRedisToken()) {
            buildToken(tokenString);
        }
    }

    public @NotNull Token buildToken(String tokenString) {
        this.token.setCreateTime(LocalDateTime.now());
        response.setHeader(tokenName, token.getToken());
        return refreshToken();
    }

    public @NotNull Token refreshToken() {
        token.setRefreshToken(NanoIdUtils.randomNanoId())
                .setRefreshTime(LocalDateTime.now());
        redisUtil.hmset(redisPrefix, token.buildMap(), refreshSeconds);
        return token;
    }

    public boolean validRedisToken() {
        LocalDateTime c = getRedisCreateTime();
        if (c == null) return false;
        LocalDateTime r = getRedisRefreshTime();
        if (r == null) return false;
        token.setCreateTime(c);
        token.setRefreshTime(r);
        return c.plusSeconds(maxSeconds).isAfter(LocalDateTime.now())
                && r.plusSeconds(refreshSeconds).isAfter(LocalDateTime.now());
    }

    public LocalDateTime getRedisCreateTime() {
        if (redisUtil.hasKey(redisPrefix) && redisUtil.hget(redisPrefix, "createTime") instanceof LocalDateTime l) {
            return l;
        }
        return null;
    }

    public LocalDateTime getRedisRefreshTime() {
        if (redisUtil.hasKey(redisPrefix) && redisUtil.hget(redisPrefix, "refreshTime") instanceof LocalDateTime l) {
            return l;
        }
        return null;
    }

    public String getTokenString() {
        String s = request.getHeader(tokenName);
        if (s == null || s.length() < 10) return NanoIdUtils.randomNanoId();
        return s;
    }

    public void destroyToken() {
        redisUtil.del(redisPrefix);
    }

    public Object getAttribute(String item) {
        return redisUtil.hget(redisPrefix, itemPrefix + item);
    }

    public @NotNull Map<String, Object> getAllAttribute() {
        final Map<Object, Object> hmGet = redisUtil.hmget(redisPrefix);
        final Map<String, Object> map = new HashMap<>();
        hmGet.forEach((key, value) -> {
            String keyS = String.valueOf(key);
            if (keyS.startsWith("data-"))
                map.put(keyS.substring(5), value);
        });
        return map;
    }

    public Map<Object, Object> getNativeAllAttribute() {
        return redisUtil.hmget(redisPrefix);
    }

    public void delAttribute(Object @NotNull ... item) {
        for (int i = 0; i < item.length; i++) {
            item[i] = itemPrefix + item[i];
        }
        redisUtil.hdel(redisPrefix, item);
    }

    public boolean setAttribute(String item, Object data) {
        return redisUtil.hset(redisPrefix, itemPrefix + item, data);
    }

    public JSONObject getRequestJson() {
        if (request.getAttribute(ConstantData.REQUEST_ATTR_MAP) instanceof JSONObject jsonObject) {
            return jsonObject;
        }
        final String contentType = request.getContentType();
        if (contentType == null) {
            return setRequestJson();
        } else if (contentType.contains("application/x-www-form-urlencoded")) {
            final Map<String, String[]> parameterMap = request.getParameterMap();
            JSONObject jsonObject = new JSONObject();
            parameterMap.forEach((key, value) -> jsonObject.put(key, value[value.length - 1]));
            return setRequestJson(jsonObject);
        } else if (contentType.contains("application/json")) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                request.getReader().lines().forEach(stringBuilder::append);
            } catch (IOException ignored) {
            }
            return setRequestJson(JSONObject.parseObject(stringBuilder.toString()));
        }
        return setRequestJson();
    }

    public JSONObject setRequestJson(JSONObject jsonObject) {
        request.setAttribute(ConstantData.REQUEST_ATTR_MAP, jsonObject);
        return jsonObject;
    }

    public JSONObject setRequestJson() {
        return setRequestJson(new JSONObject());
    }
}
