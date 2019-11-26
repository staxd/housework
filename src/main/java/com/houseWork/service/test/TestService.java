package com.houseWork.service.test;

import com.houseWork.entity.test.Test;
import com.houseWork.entity.user.Role;

public interface TestService {

    Test select(int id);

    Role selectRole(int rid);
}
