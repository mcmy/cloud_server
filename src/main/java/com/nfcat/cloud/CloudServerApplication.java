package com.nfcat.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ServletComponentScan
@EnableCaching
@SpringBootApplication
@MapperScan("com.nfcat.cloud.sql.mapper")
public class CloudServerApplication {

    public static void main(String[] args){
         SpringApplication.run(CloudServerApplication.class, args);
    }

}
