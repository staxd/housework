package com.houseWork.mapper.user;

import com.houseWork.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 用户 mapper
 * </pre>
 *
 * @author zjw
 * @date 2019/6/25 16.34
 */
@org.apache.ibatis.annotations.Mapper
@Repository
public interface UserDao extends Mapper<User>, MySqlMapper<User> {

    User findByName(@Param("username") String username);

    int insert(User user);

    /**
     * 修改用户
     * @params [map]
     * @return void
     * @date 2019/7/24 14:36
     */
    void update(Map map);

    /**
     * 查找用户
     * @params [map]
     * @return java.util.List<com.houseWork.entity.user.User>
     * @date 2019/7/24 14:01
     */
    List<User> selectByMap(Map map);

    /**
     * 查找byOpenId
     * @params [openId]
     * @return com.houseWork.entity.user.User
     * @date 2019/7/27 10:57
     */
    User selectByOpenId(String openId);
}
