package com.houseWork.mapper.test;

import com.houseWork.entity.test.Test;
import com.houseWork.entity.user.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface TestMapper {

    @Select("SELECT * ,role AS roleId FROM user WHERE id = #{id} LIMIT 1")
    @Results(value = {
        @Result(property = "roleDetail", column = "roleId",one = @One(select = "com.houseWork.mapper.test.TestMapper.selectRole",fetchType= FetchType.EAGER))
    })
    Test selectById(int id);

    @Select("SELECT * FROM role WHERE id = #{roleId} LIMIT 1")
    Role selectRole(Integer roleId);
}
