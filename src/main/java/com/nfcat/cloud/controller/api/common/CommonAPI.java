package com.nfcat.cloud.controller.api.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nfcat.cloud.annotation.*;
import com.nfcat.cloud.common.Assert;
import com.nfcat.cloud.common.utils.NanoIdUtils;
import com.nfcat.cloud.enums.ConstantData;
import com.nfcat.cloud.enums.Permission;
import com.nfcat.cloud.enums.ResultCode;
import com.nfcat.cloud.exception.AssertException;
import com.nfcat.cloud.server.HttpToken;
import com.nfcat.cloud.sql.entity.NfUser;
import com.nfcat.cloud.sql.mapper.NfUserMapper;
import com.nfcat.cloud.validate.UserLogin;
import com.nfcat.cloud.validate.UserReg;
import com.nfcat.cloud.validate.UserUpdateInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.nutz.img.Images;
import org.nutz.lang.random.R;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@Slf4j
@JSONBody
@Validated
@Controller
@AutoAuthentication
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommonAPI {

    public final NfUserMapper userMapper;
    public final HttpServletRequest request;
    public final HttpServletResponse response;

    /**
     * 设置用户登录信息
     */
    public NfUser setLoginInfo(NfUser nfUser) {
        HttpToken httpToken = HttpToken.getInstance(request);
        httpToken.setAttribute(ConstantData.USER_SESSION_DATA, nfUser);
        return nfUser;
    }

    /**
     * 验证输入的验证码
     *
     * @param token 用户token
     * @param code  验证码字符串
     */
    private void verifyCode(@NotNull HttpToken token, String code) {
        if (token.getAttribute(ConstantData.IMG_VERIFY_TIME) instanceof LocalDateTime time) {
            if (time.plusSeconds(300).isAfter(LocalDateTime.now()) &&
                    code.equals(token.getAttribute(ConstantData.IMG_VERIFY_CODE))) {
                return;
            }
        }
        throw new AssertException(ResultCode.VERIFY_CODE_FAILED);
    }


    //------------------------- 请求API  -------------------------//

    /**
     * 获取token API
     *
     * @return Response Token
     */
    @AutoGenToken
    @AutoAuthentication(Permission.VISITOR)
    @RequestMapping("/getToken")
    public Object getToken() {
        final HttpToken httpToken = HttpToken.getInstance(request);
        return ResultCode.format(ResultCode.SUCCESS, httpToken.getToken());
    }

    /**
     * 刷新token API
     *
     * @return Response Token
     */
    @RequestMapping("/refreshToken")
    public Object refreshToken() {
        final HttpToken httpToken = HttpToken.getInstance(request);
        return ResultCode.format(ResultCode.SUCCESS, httpToken.refreshToken());
    }

    /**
     * 获取图片验证码
     * Response image
     */
    @JSONBodyExclude
    @AutoAuthentication(Permission.VISITOR)
    @GetMapping("/getVerifyImage")
    public void getVerifyImage() {
        String c = R.captchaChar(4);
        try (OutputStream out = response.getOutputStream()) {
            final BufferedImage captcha = Images.createCaptcha(c, 120, 40, null, "FFF", null);
            final HttpToken token = HttpToken.getInstance(request);
            token.setAttribute(ConstantData.IMG_VERIFY_CODE, c);
            token.setAttribute(ConstantData.IMG_VERIFY_TIME, LocalDateTime.now());
            response.setContentType("image/png");
            ImageIO.write(captcha, "png", out);
        } catch (IOException ignored) {
        }
    }

    /**
     * 获取用户信息
     *
     * @return Response NfUser
     */
    @RequestMapping("/info")
    public Object info() {
        HttpToken httpToken = HttpToken.getInstance(request);
        if (httpToken.getAttribute(ConstantData.USER_SESSION_DATA) instanceof NfUser nfUser) {
            return nfUser;
        }
        return ResultCode.format(ResultCode.USER_NOT_LOGIN);

    }

    /**
     * 用户登录
     *
     * @param data 请求登录数据 RequestData
     * @return Response NfUser
     */
    @PostMapping("/login")
    @AutoAuthentication(Permission.VISITOR)
    public Object login(@Valid @RequestBody UserLogin.@NotNull RequestData data) {
        final NfUser nfUser = userMapper.selectOne(new QueryWrapper<NfUser>().lambda()
                .eq(NfUser::getUsername, data.getUsername())
                .and(wq -> wq.eq(NfUser::getPassword, data.getPassword())));
        Assert.notNull(nfUser, ResultCode.USER_LOGIN_FAIL);
        setLoginInfo(nfUser);
        return nfUser;
    }

    /**
     * 用户注册
     *
     * @param data 请求注册数据 RequestData
     * @return Response NfUser
     */
    @PostMapping("/reg")
    @AutoAuthentication(Permission.VISITOR)
    public Object reg(@Valid @RequestBody(required = false) UserReg.@NotNull RequestData data) {
        HttpToken httpToken = HttpToken.getInstance(request);
        verifyCode(httpToken, data.getVerifyCode());
        final String nanoId = NanoIdUtils.randomNanoId();
        NfUser nfUser = new NfUser()
                .setNanoId(nanoId)
                .setPassword(data.getPassword());
        switch (data.getType()) {
            case "phone" -> nfUser.setPhone(data.getPhone());
            case "email" -> nfUser.setEmail(data.getEmail());
            case "username" -> nfUser.setUsername(data.getUsername());
        }
        if (userMapper.insert(nfUser) == 1) {
            return userMapper.selectOne(new QueryWrapper<NfUser>().lambda()
                    .eq(NfUser::getNanoId, nanoId));
        }
        return ResultCode.format(ResultCode.REGISTER_FAILED);
    }

    /**
     * 修改用户信息
     *
     * @param data 请求注册数据 RequestData
     * @return Response NfUser
     */
    @PostMapping("/updateInfo")
    public Object updateUserInfo(@Valid @RequestBody(required = false) UserUpdateInfo.@NotNull RequestData data) {
        HttpToken httpToken = HttpToken.getInstance(request);
        if (httpToken.getAttribute(ConstantData.USER_SESSION_DATA) instanceof NfUser nfUser) {
            final int update = userMapper.update(null, data.getUpdateNfUserWrapper(nfUser)
                    .eq(NfUser::getId, nfUser.getId()));
            if (update > 0) {
                return setLoginInfo(userMapper.selectById(nfUser.getId()));
            }
        }
        return ResultCode.format(ResultCode.WARNING);
    }
}
