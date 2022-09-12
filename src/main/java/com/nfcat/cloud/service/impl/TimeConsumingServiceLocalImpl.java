package com.nfcat.cloud.service.impl;

import com.nfcat.cloud.service.MailService;
import com.nfcat.cloud.service.interfaces.TimeConsumingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeConsumingServiceLocalImpl implements TimeConsumingService {

    public final MailService mailService;

    @Override
    public void sendDefaultHtmlCode(String email, String subject, String user, String code) {
        mailService.sendDefaultHtmlCode(email, subject, user, code);
    }
}
