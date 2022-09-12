package com.nfcat.cloud;

import com.nfcat.cloud.service.RedisUtilService;
import com.nfcat.cloud.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudServerApplicationTests {

    @Autowired
    public MailService mailService;

    @Autowired
    public RedisUtilService redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    void sendEmail(){
//        mailService.sendDefaultHtml("775720345@qq.com","柠风猫验证码邮件","","");
        mailService.sendDefaultHtmlCode("775720345@qq.com","柠风猫验证码邮件","bm","362658");
    }

    @Test
    void incr(){
        String key = "aaa";
        System.out.println(redisUtil.redisTemplate.opsForValue().increment(key));
        System.out.println(redisUtil.redisTemplate.opsForValue().increment(key));
        System.out.println(redisUtil.redisTemplate.opsForValue().increment(key));
        System.out.println(redisUtil.redisTemplate.opsForValue().increment(key));
    }

}
