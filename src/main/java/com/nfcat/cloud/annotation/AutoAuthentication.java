package com.nfcat.cloud.annotation;

import com.nfcat.cloud.enums.Permission;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AutoAuthentication {
    int value() default Permission.USER;
}
