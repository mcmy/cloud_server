package com.nfcat.cloud.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@NoArgsConstructor
public class WXPayConfig implements com.github.wxpay.sdk.WXPayConfig {
    @Value("${pay.wxpay.appID}")
    public String appID;
    @Value("${pay.wxpay.mchID}")
    public String mchID;
    @Value("${pay.wxpay.key}")
    public String key;
    @Value("${pay.wxpay.certPath}")
    public String certPath;
    @Getter
    @Value("${pay.wxpay.payNotifyUrl}")
    public String payNotifyUrl;

    private byte[] certData;

    public WXPayConfig(String appID, String mchID, String key, String certPath, String payNotifyUrl) {
        this.appID = appID;
        this.mchID = mchID;
        this.key = key;
        this.certPath = certPath;
        this.payNotifyUrl = payNotifyUrl;
        try {
            InputStream certStream = certPath.startsWith("classpath") ?
                    Thread.currentThread().getContextClassLoader().getResourceAsStream(certPath) : new FileInputStream(certPath);
            this.certData = new byte[certPath.length()];
            assert certStream != null;
            certStream.read(this.certData);
            certStream.close();
        } catch (IOException ignored) {
            log.warn("WeChat payment certificate read error");
        }
    }


    @Override
    public String getAppID() {
        return appID;
    }

    @Override
    public String getMchID() {
        return mchID;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 10000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
