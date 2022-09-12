package com.nfcat.cloud.enums;

import com.nfcat.cloud.data.JsonResponse;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/*
 * @author 柠风猫
 * @date 2021/09/23
 * @description: 全局统一返回响应枚举类
 * */
@Getter
public enum ResultCode implements Serializable {

    //通用错误
    SUCCESS(200, "操作成功"),
    WARNING(201, "操作失败"),
    ERROR(500, "服务器内部错误"),
    FORBIDDEN(403, "拒绝请求"),
    FILE_NOT_FOUND(404, "请求数据不存在"),
    API_FAILED(1001, "接口错误"),
    VALIDATE_FAILED(1002, "参数校验失败"),
    NOT_MORE(1003, "没有内容"),
    NO_PRODUCT(1004, "商品不存在"),
    ADD_FAILED(1005, "添加失败"),
    TOO_MANY_CARTS(1006, "购物车数量已达最大限制，添加失败"),
    UPLOAD_ERROR(1007, "上传失败"),
    NO_ARTICLE(1008, "文章不存在"),

    //2000系列用户错误
    USER_NOT_EXIST(2000, "用户不存在"),
    USER_LOGIN_FAIL(2001, "用户名或密码错误"),
    USER_NOT_LOGIN(2002, "用户未登录,请先登录"),
    FORBIDDEN_TO_LOGIN(2003, "用户被禁止登录"),
    REGISTER_FAILED(2004, "注册失败"),
    IS_EXIST_USER(2005, "用户名已被注册"),
    PASSWORD_IS_TOO_SIMPLE(2006, "密码过于简单"),
    LOGIN_FAIL(2007, "登录失败"),

    //鉴权验证
    USER_NO_PERMISSION(2101, "权限不足"),
    USER_EMAIL_NOT_VERIFIED(2102, "邮箱未验证"),
    USER_PHONE_NOT_VERIFIED(2103, "手机号未验证"),
    USER_ALL_NOT_VERIFIED(2104, "手机号或邮箱未验证"),
    PHONE_CODE_ERROR(2105, "手机验证码错误"),
    NOT_PHONE_CODE(2106, "请先获取手机验证码"),
    PHONE_FORMAT_ERROR(2107, "手机号格式错误"),
    EMAIL_FORMAT_ERROR(2108, "邮箱格式错误"),
    PHONE_CODE_ERROR_TIMES_FILL(2109, "验证码错误次数过多，请重新获取验证码"),
    MISSING_PARAMETERS(2110, "参数缺失"),
    FREQUENT_OPERATION(2111, "操作频繁"),

    //格式验证
    USER_USERNAME_INPUT_FAIL(2201, "用户名格式错误"),
    USER_PASSWORD_INPUT_FAIL(2202, "密码格式错误"),
    USER_EMAIL_INPUT_FAIL(2203, "邮箱格式错误"),
    DATA_FORMAT_ERROR(2204, "数据格式错误"),

    //3000系列验证错误
    VERIFY_CODE_FAILED(3000, "验证码错误"),
    VERIFY_CODE_TIME_OVERDUE(3001, "验证码过期"),
    NOT_TOKEN(3002, "无TOKEN"),
    REDIS_SERVER_ERROR(3003, "Redis服务异常"),
    EMAIL_SEND_ERROR(3004, "邮件发送失败"),

    //数据操作错误
    INSERT_FAILED(4000, "插入失败"),
    DELETE_FAILED(4001, "删除失败"),
    MODIFY_FAILED(4002, "修改失败"),
    NONE_DATA(4003, "无数据"),

    INSUFFICIENT_BALANCE(5000, "余额不足"),
    WRONG_WAY_TO_BUY(5001, "购买方式错误"),

    SQL_ERROR(10000, "数据库错误");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Contract(value = " -> new", pure = true)
    public @NotNull
    JsonResponse toJsonResponse() {
        return new JsonResponse(code, msg);
    }

    @Contract(value = "_ -> new", pure = true)
    public @NotNull
    JsonResponse toJsonResponse(Object o) {
        return new JsonResponse(code, msg, o);
    }

}
