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
public class UserReg {

    @Data
    @VerifyAnnotation
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestData implements Serializable {
        private String username;
        private String password;
        private String verifyCode;
        private String verify_code;

        public String getVerifyCode() {
            return verifyCode == null ? verify_code : verifyCode;
        }
    }

    public static class RequestDataValidator implements ConstraintValidator<VerifyAnnotation, RequestData>, RequestValidateInterface {
        @Override
        public boolean isValid(@NotNull UserReg.RequestData data, ConstraintValidatorContext context) {
            if (!MatcherString.USERNAME.matcher(data.getUsername())) doErrMsg(ResultCode.USER_USERNAME_INPUT_FAIL);
            if (!MatcherString.PASSWORD.matcher(data.getPassword())) doErrMsg(ResultCode.USER_PASSWORD_INPUT_FAIL);
            if (data.getVerifyCode() == null || data.getVerifyCode().length() < 1)
                doErrMsg(ResultCode.VERIFY_CODE_FAILED);
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
