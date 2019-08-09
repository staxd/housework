package com.houseWork.entity.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 响应码枚举，参考HTTP状态码的语义
 * </pre>
 *
 * @author zjw
 * @date 2019/7/24 19:08.
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200),

    /**
     * 失败
     */
    FAIL(400),

    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401),


    /**
     * 接口不存在
     */
    NOT_FOUND(404),


    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500);

    public int code;
}
