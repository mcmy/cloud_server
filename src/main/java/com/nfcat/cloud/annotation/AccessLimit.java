package com.nfcat.cloud.annotation;

import com.nfcat.cloud.service.impl.AccessLimitVerificationServiceImpl;
import com.nfcat.cloud.service.interfaces.AccessLimitVerificationService;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AccessLimit {
    int defaultSeconds = 30;
    int defaultMaxCount = 20;

    int seconds() default defaultSeconds;

    int maxCount() default defaultMaxCount;

    Class<? extends AccessLimitVerificationService> getAccessLimitVerifyClass() default AccessLimitVerificationServiceImpl.class;

}
