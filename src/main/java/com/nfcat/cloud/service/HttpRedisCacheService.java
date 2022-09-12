package com.nfcat.cloud.service;

import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.CloudException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class HttpRedisCacheService {
    private final RedisUtilService redisUtil;

    private static final String redisPrefixPrefix = "nfcat:cloud:attr:";
    private static final String redisPrefixIncr = "nfcat:cloud:attr:incr:";

    public boolean setAttribute(String attr, Object o, long time) {
        return setAttribute(redisUtil, attr, o, time);
    }

    public Object getAttribute(String attr) {
        return getAttribute(redisUtil, attr);
    }

    public static boolean setAttribute(@NotNull RedisUtilService redisUtil, String attr, Object o, long time) {
        return redisUtil.set(redisPrefixPrefix + attr, o, time);
    }

    public static Object getAttribute(@NotNull RedisUtilService redisUtil, String attr) {
        return redisUtil.get(redisPrefixPrefix + attr);
    }

    static final CloudException cloudException = new CloudException(ResultCode.REDIS_SERVER_ERROR);


    public static final Random random = new Random();
}
