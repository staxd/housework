package com.houseWork.service.test;


import com.houseWork.entity.test.Test;
import com.houseWork.entity.user.Role;
import com.houseWork.mapper.test.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public Test select(int id) {
        return testMapper.selectById(id);
    }

    @Override
    public Role selectRole(int rid) {
        return testMapper.selectRole(rid);
    }
}
