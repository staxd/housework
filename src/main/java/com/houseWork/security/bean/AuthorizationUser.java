package com.houseWork.security.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * <pre>
 * 授权用户
 * </pre>
 * @author zjw
 */
@Data
@Getter
@Setter
@Builder
public class AuthorizationUser {

    //@NotBlank
    @ApiModelProperty("用户名")
    private String username;

    //@NotBlank
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("登录验证Code")
    private String platCode;

    @ApiModelProperty("用户数据包")
    private Map<String,String> platUserInfoMap;

}
