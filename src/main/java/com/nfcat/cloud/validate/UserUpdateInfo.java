package com.nfcat.cloud.validate;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.nfcat.cloud.common.Encrypt;
import com.nfcat.cloud.enums.MatcherString;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import com.nfcat.cloud.interfaces.RequestValidateInterface;
import com.nfcat.cloud.sql.entity.NfUser;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class UserUpdateInfo {

    public static class RequestData extends NfUser {


        @Override
        public String getPassword() {
            return Encrypt.encryptUserPassword(super.getPassword());
        }

        /**
         * 获取用户更新数据
         *
         * @return LambdaUpdateWrapper<NfUser>
         */
        public LambdaUpdateWrapper<NfUser> getUpdateNfUserWrapper(NfUser oldNfUser) {
            return new GenUpdateWrapper().getLambda(oldNfUser);
        }

        class GenUpdateWrapper {
            int num = 0;
            final LambdaUpdateWrapper<NfUser> lambda = new UpdateWrapper<NfUser>().lambda();
            static final List<SFunction<NfUser, ?>> sFunctionList = genFunctions(
                    NfUser::getUsername,
                    NfUser::getPassword,
                    NfUser::getEmail,
                    NfUser::getHeaderImg,
                    NfUser::getNickname,
                    NfUser::getPhone,
                    NfUser::getSex
            );

            @SafeVarargs
            @Contract(pure = true)
            public static @NotNull List<SFunction<NfUser, ?>> genFunctions(SFunction<NfUser, ?>... sFunctions) {
                return Arrays.asList(sFunctions);
            }

            public LambdaUpdateWrapper<NfUser> getLambda(NfUser oldNfUser) {
                for (SFunction<NfUser, ?> nfUserSFunction : sFunctionList) {
                    Object o = nfUserSFunction.apply(RequestData.this);
                    System.out.printf("---%s---%s---\n",o,nfUserSFunction.apply(oldNfUser));
                    if (o != null && !o.equals(nfUserSFunction.apply(oldNfUser))) {
                        lambda.set(nfUserSFunction, o);
                        ++num;
                    }
                }
                if (num == 0) {
                    throw new AssertException(ResultCode.MISSING_PARAMETERS);
                }
                return lambda;
            }
        }

    }


    static class RequestDataValidator implements ConstraintValidator<VerifyAnnotation, RequestData>, RequestValidateInterface {
        @Override
        public boolean isValid(@NotNull RequestData data, ConstraintValidatorContext context) {
            if (data.getPassword() != null && !MatcherString.PASSWORD.matcher(data.getPassword())) {
                doErrMsg(ResultCode.USER_PASSWORD_INPUT_FAIL);
            }
            if (data.getEmail() != null && !MatcherString.EMAIL.matcher(data.getEmail())) {
                doErrMsg(ResultCode.EMAIL_FORMAT_ERROR);
            }
            if (data.getPhone() != null && !MatcherString.PHONE.matcher(data.getPhone())) {
                doErrMsg(ResultCode.PHONE_FORMAT_ERROR);
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
