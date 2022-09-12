package com.nfcat.cloud.validate;

import com.nfcat.cloud.enums.MatcherString;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.interfaces.RequestValidateInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.Serializable;
import java.lang.annotation.*;

@Slf4j
public class PhoneVerifyCode {

    @Data
    @VerifyAnnotation
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestData implements Serializable {
        private String phone;
    }

    @NoArgsConstructor
    public static class RequestDataValidator implements ConstraintValidator<VerifyAnnotation, RequestData>, RequestValidateInterface {
        @Override
        public boolean isValid(@NotNull RequestData data, ConstraintValidatorContext context) {
            if (data.getPhone() == null
                    || !MatcherString.EMAIL.matcher(data.getPhone())) {
                doErrMsg(ResultCode.USER_EMAIL_INPUT_FAIL);
            }
            return true;
        }
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = {ElementType.TYPE})
    @Constraint(validatedBy = RequestDataValidator.class)
    public @interface VerifyAnnotation {
        String message() default "verification failed";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

}
