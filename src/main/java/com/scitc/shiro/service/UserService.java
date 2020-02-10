package com.scitc.shiro.service;

import com.scitc.shiro.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserService {
    User findUserRoleAndPermission(String username);

    User findUserByName(String username);
    // 注册用户
    void  register(User user);
}
