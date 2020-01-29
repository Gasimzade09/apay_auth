package az.ms.apay_auth.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum ExceptionHolder {
    INTERNAL_EXCEPTION(7000, "pro.error.7000"),
    LOGIN_EXCEPTION(7001, "pro.error.7001"),
    USER_NOT_FOUND_EXCEPTION(7002, "pro.error.7002"),
    CARD_NOT_FOUND_EXCEPTION(7003, "pro.error.7003");
    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
