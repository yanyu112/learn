package com.billow.common.enums;

/**
 * redis 的key
 *
 * @author LiuYongTao
 * @date 2018/5/24 9:52
 */
public enum RdsKeyEnum {

    /**
     * "whiteList:", "白名单前缀"，如 whiteList:springApplicationName:clientIP
     */
    WHITE_LIST("whiteList:", "白名单前缀");

    private String key;
    private String value;

    RdsKeyEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public RdsKeyEnum setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RdsKeyEnum setValue(String value) {
        this.value = value;
        return this;
    }
}