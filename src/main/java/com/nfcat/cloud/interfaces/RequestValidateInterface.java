package com.nfcat.cloud.interfaces;

import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import org.jetbrains.annotations.NotNull;

import javax.validation.ConstraintValidatorContext;

public interface RequestValidateInterface {

    default boolean doErrMsg(@NotNull ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        return false;
    }

    default void doErrMsg(ResultCode resultCode) {
        throw new AssertException(resultCode);
    }
}
