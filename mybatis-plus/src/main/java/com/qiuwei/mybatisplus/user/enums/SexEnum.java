package com.qiuwei.mybatisplus.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 性别
 *
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-09-20.
 */
@Getter
public enum SexEnum {

    MAN(1, "男"),
    WOMEN(2, "女");

    @EnumValue
    @JsonValue
    private int code;

    private String msg;

    SexEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
