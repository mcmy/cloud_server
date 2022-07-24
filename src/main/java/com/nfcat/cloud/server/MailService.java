package com.nfcat.cloud.server;

import com.nfcat.cloud.config.WebConfig;
import com.nfcat.cloud.exception.EmailSendException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public final class MailService {
    public final JavaMailSender mailSender;
    public final FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendEmailCode(String email, String code) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("title", "邮箱验证码");
        map.put("text", code);
        map.put("com", "By 柠风猫");
        // 发送邮件模板
        try (StringWriter writer = new StringWriter()) {
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("mail_verify.ftl", "UTF-8");
            template.process(map, writer);
            sendHtmlEmail(email, "邮箱验证码", writer.toString());
        } catch (Exception ignored) {
            log.info("邮件发送失败 收件人:" + email);
            throw new EmailSendException();
        }
    }

    public void sendTextEmail(String email, String subject, String text) throws Exception {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(WebConfig.USER_EMAIL);
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        try {
            mailSender.send(mailMessage);
        } catch (Exception ignored) {
            throw new EmailSendException();
        }
    }

    public void sendHtmlEmail(String email, String subject, String text) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(WebConfig.USER_EMAIL);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(mimeMessage);
        } catch (Exception ignored) {
            throw new EmailSendException();
        }
    }
}
