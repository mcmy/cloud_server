package com.nfcat.cloud.common;

import com.nfcat.cloud.config.WebConfig;
import com.nfcat.cloud.common.utils.NfUtils;

public class Encrypt {
    /**
     * 加密密码
     * @param password 原密码
     * @return 加密后密码
     */
    public static String encryptUserPassword(String password) {
        if (password == null) return null;
        return NfUtils.pwdEncrypt(password, WebConfig.SALT);
    }
}
