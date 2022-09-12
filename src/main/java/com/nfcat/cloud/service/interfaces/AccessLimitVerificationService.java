package com.nfcat.cloud.service.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface AccessLimitVerificationService {
    boolean verify(HttpServletRequest request);

    String getClientID(HttpServletRequest request);
}
