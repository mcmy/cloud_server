package com.nfcat.cloud.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@ResponseBody
public @interface JSONBodyExclude {
}
