package com.yangjiahai.ebookdownload.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author yangjiahai
 */
@Getter
@ToString
public enum ResultCodeEnum {
    SUCCESS(true, 20000, "成功"),
    USER_INEXISTENCE(false, 20001, "用户不存在"),
    PASSWORD_ERROR(false, 20002, "密码错误"),
    NO_PERMISSION(false, 20003, "没有权限"),
    NO_RESOURCES(false, 20004, "资源不存在"),
    REQUEST_ERROR(false, 20005, "请求方式错误"),
    CAPTCHA_ERROR(false, 20006, "验证码错误"),
    ACCOUNT_DISABLED(false, 20007, "账号被锁定"),
    TOKEN_EXPIRED(false, 20008, "令牌已过期"),
    UN_LOGIN(false, 20009, "未登录"),
    REPEAT_SIGNIN(false, 20010, "重复签到"),
    UNKNOWN_ERROR(false, 20011, "未知错误"),
    Frequent_Operation(false, 20012, "频繁操作");
    private boolean success;
    private int code;
    private String message;

    ResultCodeEnum(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

}
