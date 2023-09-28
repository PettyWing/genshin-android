package com.youer.genshin.resp;

import java.io.Serializable;

import com.youer.genshin.constants.ResultCode;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 374551197782119716L;

    private boolean success = false;
    private T result;

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessageInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setSuccessWithResult(T result) {
        this.success = true;
        this.result = result;
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = new Result<>();
        result.setSuccessWithResult(t);
        return result;
    }

    public static <T> Result<T> fail(String errorCode, String errorMessage) {
        Result result = new Result();
        result.setMessageInfo(errorCode, errorMessage);
        return result;
    }

    public static <T> Result<T> fail(ResultCode resultCode) {
        Result result = new Result();
        result.setMessageInfo(resultCode.getCode(), resultCode.getMessage());
        return result;
    }

    public static Result copyResult(Result result) {
        Result copy = new Result();
        copy.setSuccess(result.isSuccess());
        copy.setMessage(result.getMessage());
        copy.setCode(result.getCode());
        return copy;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
