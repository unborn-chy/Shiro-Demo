package com.scitc.shiro.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {

    int save(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}
