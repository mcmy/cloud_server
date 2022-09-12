package com.nfcat.cloud.service.impl;

import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import com.nfcat.cloud.service.HttpToken;
import com.nfcat.cloud.service.interfaces.VerifyService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 使用验证码验证
 */
@Slf4j
@Service
public class VerifyServiceByCodeImpl implements VerifyService {
    /**
     * 设置验证码
     *
     * @param token 用户token
     * @param code  验证码字符串
     */
    @Override
    public void setVerifyCode(@NotNull HttpToken token, String code) {
        token.setAttribute(ConstantData.IMG_VERIFY_CODE, code);
        token.setAttribute(ConstantData.IMG_VERIFY_TIME, LocalDateTime.now());
    }

    /**
     * 验证输入的验证码
     *
     * @param token 用户token
     * @param code  验证码字符串
     */
    @Override
    public void verifyCode(@NotNull HttpToken token, String code) {
        if (token.getAttribute(ConstantData.IMG_VERIFY_TIME) instanceof LocalDateTime time) {
            if (time.plusSeconds(300).isAfter(LocalDateTime.now()) &&
                    code.equals(token.getAttribute(ConstantData.IMG_VERIFY_CODE))) {
                return;
            }
            token.delAttribute(ConstantData.IMG_VERIFY_TIME, ConstantData.IMG_VERIFY_CODE);
        }
        throw new AssertException(ResultCode.VERIFY_CODE_FAILED);
    }
}
