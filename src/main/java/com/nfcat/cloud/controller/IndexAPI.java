package com.nfcat.cloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.cloud.annotation.JSONBody;
import com.nfcat.cloud.config.WebConfig;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@JSONBody
@Validated
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class IndexAPI {

    public final NfUserMapper userMapper;
    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public final RedisUtil redisUtil;

    @Contract(" -> new")
    private @NotNull HttpToken getToken() {
        return new HttpToken(redisUtil, request, response);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody UserLogin.@NotNull RequestData data) {
//        HttpToken httpToken = getToken();
        final NfUser nfUser = userMapper.selectOne(new QueryWrapper<NfUser>().lambda()
                .eq(NfUser::getUsername, data.getUsername())
                .and(wq -> wq.eq(NfUser::getPassword, NfUtils.pwdEncrypt(data.getPassword(), WebConfig.SALT))));
        Assert.notNull(nfUser, ResultCode.USER_LOGIN_FAIL);
        return nfUser;
    }

    @PostMapping("/reg")
    public Object reg(@Valid @RequestBody(required = false) UserReg.@NotNull RequestData data/*, HttpToken httpToken*/) {
        return data;
    }

    @PostMapping("/test")
    public Object test() {
        HttpToken httpToken = getToken();
        httpToken.setAttribute("nb1", 1);
        httpToken.setAttribute("nb", 1);
        httpToken.delAttribute("nb");
        Map<String, Object> map = new HashMap<>();
        map.put("query", httpToken.getRequest().getQueryString());
        map.put("nb1", httpToken.getAttribute("nb1"));
        map.put("map", httpToken.getAllAttribute());
        map.put("nMap", httpToken.getNativeAllAttribute());
        return map;
    }
}
