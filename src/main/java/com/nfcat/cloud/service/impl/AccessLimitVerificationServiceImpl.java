package com.nfcat.cloud.service.impl;

import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import com.nfcat.cloud.service.interfaces.AccessLimitVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessLimitVerificationServiceImpl implements AccessLimitVerificationService {

    @Override
    public boolean verify(HttpServletRequest request) {
        throw new AssertException(ResultCode.FREQUENT_OPERATION);
    }

    @Override
    public String getClientID(@NotNull HttpServletRequest request) {
       return DigestUtils.md5DigestAsHex((request.getServletPath() + getIpAddr(request)).getBytes(StandardCharsets.UTF_8));
    }

    public String getIpAddr(@NotNull HttpServletRequest request) {
        //目前则是网关ip
        String ip = request.getHeader("X-Real-IP");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !"".equals(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                //只获取第一个值
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            //取不到真实ip则返回空，不能返回内网地址。
            return "";
        }
    }
}
