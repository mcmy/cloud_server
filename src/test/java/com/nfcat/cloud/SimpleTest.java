package com.nfcat.cloud;

import com.nfcat.cloud.config.WebConfig;
import com.nfcat.cloud.utils.NfUtils;

public class SimpleTest {
    public static void main(String[] args) {
        System.out.println(NfUtils.pwdEncrypt("123456", "98L33vEIxPIiYfpG"));
    }
}
