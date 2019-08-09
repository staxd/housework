package com.houseWork.entity.response;

/**
 * <pre>
 * 将结果转换为封装后的对象
 * </pre>
 *
 * @author zjw
 * @date 2019/7/24 19:16.
 */

public class ResponseResult<T>{
    private final static String SUCCESS = "success";

    /**
     * 成功响应
     * @params []
     * @return com.houseWork.entity.response.Result<T>
     * @date 2019/7/24 19:10
     */
    public static <T> Result<T> successResponse() {
        return new Result<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS).setStatus(true);
    }

   /**
    * 成功响应
    * @params [data]
    * @return com.houseWork.entity.response.Result<T>
    * @date 2019/7/24 19:10
    */
    public static <T> Result<T> successResponse(T data) {
        return new Result<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS).setData(data).setStatus(true);
    }

    /**
     * 失败响应
     * @params [message]
     * @return com.houseWork.entity.response.Result<T>
     * @date 2019/7/24 19:11
     */
    public static <T> Result<T> errResponse(String message) {
        return new Result<T>().setCode(ResultCode.FAIL).setMsg(message).setStatus(false);
    }

}
