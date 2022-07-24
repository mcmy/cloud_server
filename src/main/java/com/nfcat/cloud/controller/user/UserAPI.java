package com.nfcat.cloud.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.cloud.annotation.JSONBody;
import com.nfcat.cloud.config.WebConfig;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.server.HttpToken;
import com.nfcat.cloud.sql.entity.NfUser;
import com.nfcat.cloud.sql.mapper.NfUserMapper;
import com.nfcat.cloud.utils.Assert;
import com.nfcat.cloud.utils.NfUtils;
import com.nfcat.cloud.utils.RedisUtil;
import com.nfcat.cloud.validate.UserLogin;
import com.nfcat.cloud.validate.UserReg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@JSONBody
@Validated
@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserAPI {
    public final NfUserMapper userMapper;
    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public final RedisUtil redisUtil;

    @Contract(" -> new")
    private @NotNull HttpToken getToken() {
        return new HttpToken(redisUtil, request, response);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody UserLogin.@NotNull RequestData data){
        final NfUser nfUser = userMapper.selectOne(new QueryWrapper<NfUser>().lambda()
                .eq(NfUser::getUsername, data.getUsername())
                .and(wq -> wq.eq(NfUser::getPassword, NfUtils.pwdEncrypt(data.getPassword(), WebConfig.SALT))));
        Assert.notNull(nfUser, ResultCode.USER_LOGIN_FAIL);
        HttpToken httpToken = getToken();
        httpToken.setAttribute(ConstantData.USER_SESSION_DATA,nfUser);
        return nfUser;
    }

    @PostMapping("/reg")
    public Object reg(@Valid @RequestBody(required = false) UserReg.@NotNull RequestData data) {
        //TODO 登录
        return data;
    }

    @PostMapping("/info")
    public Object info() {
        HttpToken httpToken = getToken();
        return httpToken.getAttribute(ConstantData.USER_SESSION_DATA);
    }


}
