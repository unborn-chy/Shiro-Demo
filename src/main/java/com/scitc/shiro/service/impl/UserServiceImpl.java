package com.scitc.shiro.service.impl;

import com.scitc.shiro.mapper.UserMapper;
import com.scitc.shiro.pojo.User;
import com.scitc.shiro.service.UserRoleService;
import com.scitc.shiro.service.UserService;
import com.scitc.shiro.shiro.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public User findUserRoleAndPermission(String username) {
        return userMapper.findUserRoleAndPermission(username);
    }

    @Override
    public User findUserByName(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public void  register(User user) {
        userMapper.save(user);
    }
}
