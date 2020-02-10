package com.scitc.shiro.service;

import com.scitc.shiro.pojo.User;
import org.apache.ibatis.annotations.Param;


public interface UserRoleService {
    // 用户绑定角色
    int save( Integer userId, Integer roleId);
}
