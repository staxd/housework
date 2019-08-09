package com.houseWork.service.user;

import com.houseWork.entity.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 通过名字查找
     * @params [username]
     * @return com.houseWork.entity.user.User
     * @date 2019/7/27 11:20
     */
    User findByname(String username);

    /**
     * 添加用户
     * @params [user]
     * @return void
     * @date 2019/7/23 19:34
     */
    void addUser(User user);

    /**
     * 查找用户列表
     * @params [map]
     * @return java.util.List<com.houseWork.entity.user.User>
     * @date 2019/7/24 14:00
     */
    List<User> selectByMap(Map map);

    /**
     * 修改用户
     * @params [map]
     * @return void
     * @date 2019/7/24 14:34
     */
    void updateUser(Map map);
}
