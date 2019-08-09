package com.houseWork.entity.response;

/**
 * <pre>
 * 返回对象实体
 * </pre>
 *
 * @author zjw
 * @date 2019/7/24 19:11.
 */
public class Result<T> {

    /**
     * 返回code
     */
    public int code;

    /**
     * 返回信息
     */
    private String msg;

    private Boolean status;

    public Result() {
        this.status = true;
        this.code = ResultCode.SUCCESS.code;
    }

    /**
     * 返回具体信息
     */
    private T data;

    public Result<T> setCode(ResultCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Result<T> setStatus(Boolean status) {
        this.status = status;
        return this;
    }
}
