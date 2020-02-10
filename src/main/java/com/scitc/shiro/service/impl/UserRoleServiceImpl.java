package com.scitc.shiro.service.impl;


import com.scitc.shiro.mapper.UserRoleMapper;
import com.scitc.shiro.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl  implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public int save(Integer userId, Integer roleId) {
        return userRoleMapper.save(userId,roleId);
    }
}
