package com.nfcat.cloud.validate;

import com.nfcat.cloud.common.Encrypt;
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

    public static class RegType{
        public static final String USERNAME = "username";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
    }

    @Data
    @VerifyAnnotation
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestData implements Serializable {
        private String username;
        private String phone;
        private String email;
        private String password;
        private String type;
        private String verifyCode;
        private String verify_code;


        public String getPassword() {
            return Encrypt.encryptUserPassword(password);
        }

        public String getVerifyCode() {
            return verifyCode == null ? verify_code : verifyCode;
        }
    }

    @NoArgsConstructor
    public static class RequestDataValidator implements ConstraintValidator<VerifyAnnotation, RequestData>, RequestValidateInterface {
        @Override
        public boolean isValid(@NotNull RequestData data, ConstraintValidatorContext context) {
            if (!MatcherString.PASSWORD.matcher(data.getPassword())) doErrMsg(ResultCode.USER_PASSWORD_INPUT_FAIL);
            if (data.getVerifyCode() == null || data.getVerifyCode().length() < 1)
                doErrMsg(ResultCode.VERIFY_CODE_FAILED);
            switch (data.getType()) {
                case RegType.PHONE -> {
                    if (!MatcherString.PHONE.matcher(data.getPhone()))
                        doErrMsg(ResultCode.PHONE_FORMAT_ERROR);
                    return true;
                }
                case RegType.EMAIL -> {
                    if (!MatcherString.EMAIL.matcher(data.getEmail()))
                        doErrMsg(ResultCode.EMAIL_FORMAT_ERROR);
                    return true;
                }
                case RegType.USERNAME -> {
                    if (!MatcherString.USERNAME.matcher(data.getUsername()))
                        doErrMsg(ResultCode.USER_USERNAME_INPUT_FAIL);
                    return true;
                }
            }
            doErrMsg(ResultCode.MISSING_PARAMETERS);
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
