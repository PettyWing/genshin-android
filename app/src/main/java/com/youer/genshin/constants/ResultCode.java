package com.youer.genshin.constants;

/**
 * 结果码
 *
 * @author motong.wyd
 */
public enum ResultCode {

    /**
     * 唯一标识成功
     */
    SUCCESS("SUCCESS", "成功"),

    /**
     * 参数错误
     */
    PARAM_ERROR("PARAM_ERROR", "参数错误"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),
    ;

    private String code;

    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isFailure() {
        return this != SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
