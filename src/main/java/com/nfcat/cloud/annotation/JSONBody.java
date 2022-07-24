package com.nfcat.cloud.annotation;

import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface JSONBody {
    boolean cache() default false;

    String cacheName() default "";

    long cacheTimeSeconds() default 38400L;

    SerializerFeature[] serializerFeature() default {};
}
