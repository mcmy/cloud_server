package com.nfcat.cloud.validate;

import com.nfcat.cloud.common.Encrypt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Slf4j
public class UserLogin {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestData implements Serializable {
        @Length(min = 4, max = 15, message = "用户名错误")
        private String username;
        @Length(min = 8, max = 20, message = "密码格式错误")
        private String password;

        public String getPassword() {
            return Encrypt.encryptUserPassword(password);
        }
    }
}
