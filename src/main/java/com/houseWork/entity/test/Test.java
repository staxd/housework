package com.houseWork.entity.test;

import com.houseWork.entity.user.Role;
import lombok.Data;

@Data
public class Test {
    private Integer id;
    private String openId;
    private String username;    //用户名
    private String password;    //用户密码
    private String telephone;   //电话号码
    private Integer role;        //用户角色
    private String image;       //用户头像
    private Integer sex;

    private Role roleDetail;


}
