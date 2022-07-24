package com.nfcat.cloud.config;

import com.nfcat.cloud.utils.NfUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

@Slf4j
@Configuration
public class ResourceLoadConfig {
    public static Font font = null;
    public static List<File> verifyImageData = new ArrayList<>();

    @Value("${spring.freemarker.template-loader-path}")
    public String ftlPath;

    @Bean
    public void setVerifyImg() {
        try {
            ClassPathResource resource = new ClassPathResource("files/verifyImg");
            File file = new File(resource.getURI() + "/");
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                if (files == null || files.length < 1 || files.length > 30) {
                    throw new NullPointerException("no verify images or too many verify images");
                }
                verifyImageData.addAll(Arrays.asList(files));
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    @Bean
    public void createFont() {
        InputStream is = NfUtils.getResourcesInputStream("files/default.ttf");
        if (is == null) {
            log.warn("Failed to load font file stream");
            return;
        }
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 30F);
        } catch (FontFormatException | IOException e) {
            log.warn("Failed to load font file", e);
        }
    }

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath(ftlPath);
        Map<String, Object> variables = new HashMap<>();
        variables.put("xml_escape", "fmXmlEscape");
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }
}
