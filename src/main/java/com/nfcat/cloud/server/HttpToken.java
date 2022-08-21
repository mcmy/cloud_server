package com.nfcat.cloud.server;

import com.nfcat.cloud.common.utils.NanoIdUtils;
import com.nfcat.cloud.common.utils.RedisUtil;
import com.nfcat.cloud.data.Token;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpToken {

    private final RedisUtil redisUtil;
    private final Token token;

    private String redisPrefix;

    private static final String tokenName = "X-AUTH-TOKEN";
    private static final long maxSeconds = 86400;
    private static final long refreshSeconds = 7200;
    private static final String itemPrefix = "data-";
    private static final String redisPrefixPrefix = "nfcat:cloud:token:";

    /**
     * 获取token(不存在抛出异常)
     *
     * @param request HttpServletRequest
     * @return HttpToken
     */
    public static HttpToken getInstance(@NotNull HttpServletRequest request) {
        if (request.getAttribute(ConstantData.HTTP_TOKEN) instanceof HttpToken httpToken) {
            return httpToken;
        }
        throw new AssertException(ResultCode.NOT_TOKEN);
    }

    /**
     * 新建token
     * @param redisUtil RedisUtil
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public HttpToken(RedisUtil redisUtil, HttpServletRequest request, HttpServletResponse response) {
        this.redisUtil = redisUtil;
        this.token = new Token();
        final String requestTokenString = getRequestTokenString(request);
        if (requestTokenString != null) {
            redisPrefix = redisPrefixPrefix + requestTokenString;
            if (validRedisToken()) {
                //用户上传token并且token有效
                token.setToken(requestTokenString);
                return;
            }
        }
        //用户上传token但是token无效 || 用户未上传token
        LocalDateTime now = LocalDateTime.now();
        token.setToken(NanoIdUtils.randomNanoId())
                .setCreateTime(now)
                .setRefreshTime(now);
        redisPrefix = redisPrefixPrefix + token.getToken();
        redisUtil.hmset(redisPrefix,token.buildMap(),refreshSeconds);
        response.setHeader(tokenName, token.getToken());
    }

    /**
     * 获取用户请求的token值
     * 如果不存在返回null
     *
     * @param request HttpServletRequest
     * @return tokenString
     */
    public static @Nullable String getRequestTokenString(@NotNull HttpServletRequest request) {
        if (request.getAttribute(ConstantData.HTTP_TOKEN_STRING) instanceof String s) {
            return s;
        }
        String sQuery = request.getParameter("token");
        if (sQuery != null && sQuery.length() > 10) return setRequestTokenStringAttr(request, sQuery);
        String sHeader = request.getHeader(tokenName);
        if (sHeader != null && sHeader.length() > 10) return setRequestTokenStringAttr(request, sHeader);
        return null;
    }

    /**
     * 设置输入tokenString Attr
     * @param request HttpServletRequest
     * @param tokenString tokenString
     * @return tokenString
     */
    public static String setRequestTokenStringAttr(@NotNull HttpServletRequest request, String tokenString) {
        request.setAttribute(ConstantData.HTTP_TOKEN_STRING, tokenString);
        return tokenString;
    }

    /**
     * 验证token是否过期
     * @return is
     */
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

    /**
     * 续期token
     * @return Token
     */
    public @NotNull Token refreshToken() {
        token.setRefreshTime(LocalDateTime.now());
        redisUtil.hset(redisPrefix, "refreshTime", token.getRefreshTime(), refreshSeconds);
        return token;
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
}
