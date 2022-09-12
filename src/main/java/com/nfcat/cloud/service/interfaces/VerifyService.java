package com.nfcat.cloud.service.interfaces;

import com.nfcat.cloud.service.HttpToken;
import org.jetbrains.annotations.NotNull;

public interface VerifyService {
    void setVerifyCode(@NotNull HttpToken token, String code);
    void verifyCode(@NotNull HttpToken token, String code);
}
