package com.account.model;

public class Result<T> {

    private int code;

    private boolean success;

    private T data;

    private String msg;

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = code == 0;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> Result<T> ok(T data) {
        return new Result(0, data, "");
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, null, msg);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(1, null, msg);
    }
}
