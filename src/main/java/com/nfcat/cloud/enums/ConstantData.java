package com.nfcat.cloud.enums;

import com.alibaba.fastjson.serializer.SerializerFeature;

public class ConstantData {
    public static final String IMG_VERIFY_CODE = "img_verify_code";
    public static final String IMG_VERIFY_TIME = "img_verify_time";
    public static final String PHONE_VERIFY_CODE = "phone_verify_code";
    public static final String PHONE_VERIFY_TIME = "phone_verify_code_time";
    public static final String EMAIL_VERIFY_CODE = "email_verify_code";
    public static final String EMAIL_VERIFY_TIME = "email_verify_code_time";
    public static final String USER_SESSION_DATA = "userData";
    public static final String LOGIN_TIME = "loginTime";
    public static final String REQUEST_ATTR_MAP = "requestMap";
    public static final String REQUEST_ATTR_MAP_STRING = "requestMapString";
    public static final String REQUEST_ATTR_MAP_MD5 = "requestMapMd5";
    public static final String CACHE_FIELD = "cacheField";
    public static final String REDIS_CACHE_PREFIX = "nf:cache:";
    public static final String REDIS_CONFIG_PREFIX = "nf:config:";
    public static final String REDIS_USER_CACHE_PREFIX = "nf:user:cache";
    public static final String REDIS_DATA_PREFIX = "nf:data:";
    public static final String WX_CALL_BACK_JSON = "callBackJson";
    public static final String WX_CALL_BACK_CREATE_TIME = "callBackJsonCreateTime";
    public static final String WX_CALL_BACK_EXPIRED_TIME = "callBackJsonExpiredTime";
    public static final String WX_INFO = "wxInfo";
    public static final String WX_LOCAL_LOGIN_BACK = "wxLocalLoginBack";
    public static final String HTTP_TOKEN = "http_token";
    public static final String HTTP_TOKEN_STRING = "http_token_string";

    public static final SerializerFeature[] serializerFeatures = new SerializerFeature[]{
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse,
            SerializerFeature.DisableCircularReferenceDetect
    };
}
