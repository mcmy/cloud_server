package com.nfcat.cloud.service.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface AccessLimitVerificationService {
    boolean verify(HttpServletRequest request);

    void liftTheBan(HttpServletRequest request);

    String getClientID(HttpServletRequest request);
}
