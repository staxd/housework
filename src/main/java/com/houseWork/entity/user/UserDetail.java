package com.houseWork.entity.user;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@Builder
public class UserDetail {

    private Integer id;
    private String openId;
    private String username;    //用户名
    private String password;    //用户密码
    private String telephone;   //电话号码
    private String role;        //用户角色
    private String image;       //用户头像
    private Integer sex;

    private Integer roleId;
    private String roleName;
    private String rolePermission;

}
