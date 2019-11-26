package com.houseWork.entity.user;


import lombok.Data;

import java.util.Date;

@Data
public class Role {

    private Integer id;
    private String role_name;
    private String role_permission;
    private Date create_time;
}
