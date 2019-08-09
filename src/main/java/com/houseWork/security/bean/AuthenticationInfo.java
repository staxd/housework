package com.houseWork.security.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;


/**
 * <pre>
 * 身份验证信息
 * </pre>
 * @author zjw
 */
@Getter
@AllArgsConstructor
public class AuthenticationInfo<T> implements Serializable {

    private final  String token;

    private final T user;
}
