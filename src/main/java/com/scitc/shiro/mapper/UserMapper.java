package com.scitc.shiro.mapper;

import com.scitc.shiro.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper {
    // 根据用户名查询用户的所有信息
     User findUserRoleAndPermission(@Param("username") String username);
     //用户名查询用户
    User findUserByName(@Param("username") String username);
     // 注册用户
     int save(User user);
}
