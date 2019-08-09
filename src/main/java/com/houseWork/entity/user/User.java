package com.houseWork.entity.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "open_id")
    @ApiModelProperty(value = "open_id")
    private String openId;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String username;    //用户名

    @Column(name = "password")
    @ApiModelProperty(value = "用户密码")
    private String password;    //用户密码

    @Column(name = "telephone")
    @ApiModelProperty(value = "电话号码")
    private String telephone;   //电话号码

    @Column(name = "role")
    @ApiModelProperty(value = "用户角色")
    private String role;        //用户角色

    @Column(name = "image")
    @ApiModelProperty(value = "用户头像")
    private String image;       //用户头像

    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private Integer sex;
}