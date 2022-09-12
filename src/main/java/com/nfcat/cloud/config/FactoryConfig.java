package com.nfcat.cloud.config;

import com.nfcat.cloud.service.impl.AccessLimitVerificationServiceImpl;
import com.nfcat.cloud.service.impl.TimeConsumingServiceLocalImpl;
import com.nfcat.cloud.service.impl.VerifyServiceByCodeImpl;
import com.nfcat.cloud.service.interfaces.AccessLimitVerificationService;
import com.nfcat.cloud.service.interfaces.TimeConsumingService;
import com.nfcat.cloud.service.interfaces.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@RequiredArgsConstructor
public class FactoryConfig {

    public final TimeConsumingServiceLocalImpl timeConsumingServiceImpl;
    public final AccessLimitVerificationServiceImpl accessLimitVerificationServiceImpl;
    public final VerifyServiceByCodeImpl verifyServiceByCode;

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public TimeConsumingService timeConsumingService() {
        return timeConsumingServiceImpl;
    }

    @Bean
    public AccessLimitVerificationService accessLimitVerificationService() {
        return accessLimitVerificationServiceImpl;
    }

    @Bean
    public VerifyService verifyService() {
        return verifyServiceByCode;
    }
}
