package com.nfcat.cloud.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig {
    @Value("${web-config.salt}")
    public String salt;

    @Value("${web-config.img-verify-expires-timeout}")
    public Duration img_verify_expires_time;

    @Value("${web-config.phone-code-timeout}")
    public Duration phone_code_time;

    @Value("${web-config.session-keep-max-time}")
    public Duration session_keep_max_time;

    @Value("${web-config.content.length.format.max}")
    public long contentLengthFormatMax;

    @Getter
    @Value("${spring.mail.user-email}")
    public String userEmail;

    public static String SALT;
    public static String USER_EMAIL;
    public static long IMG_VERIFY_EXPIRES_TIMEOUT;
    public static long PHONE_CODE_TIMEOUT;
    public static long SESSION_KEEP_MAX_TIME;
    public static long CONTENT_LENGTH_FORMAT_MAX;

    @Bean("WebConfig")
    public void setConfig() {
        refresh();
    }

    public void refresh() {
        SALT = salt;
        IMG_VERIFY_EXPIRES_TIMEOUT = img_verify_expires_time.toMillis();
        PHONE_CODE_TIMEOUT = phone_code_time.toMillis();
        SESSION_KEEP_MAX_TIME = session_keep_max_time.toMillis();
        CONTENT_LENGTH_FORMAT_MAX = contentLengthFormatMax;
        USER_EMAIL = userEmail;
    }
}
