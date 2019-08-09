package com.houseWork.service.user.Impl;

import com.houseWork.entity.user.User;
import com.houseWork.mapper.user.UserDao;
import com.houseWork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findByname(String username) { User userinfo = userDao.findByName(username);return userinfo; }

    @Override
    public void addUser(User user) {
        User us = userDao.selectByOpenId(user.getOpenId());
        if(us!=null){
            Map map = new HashMap();
            map.put("id",us.getId());
            map.put("openId",user.getOpenId());
            map.put("username",user.getUsername());
            map.put("password",user.getPassword());
            map.put("telephone",user.getTelephone());
            map.put("role",user.getRole());
            map.put("image",user.getImage());
            userDao.update(map);
        }else {
            userDao.insert(user);
        }
    }

    @Override
    public List<User> selectByMap(Map map) { return userDao.selectByMap(map); }

    @Override
    public void updateUser(Map map) { userDao.update(map);}

}
