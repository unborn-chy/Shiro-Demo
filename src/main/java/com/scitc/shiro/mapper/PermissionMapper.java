package com.scitc.shiro.mapper;

import com.scitc.shiro.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {
    // 查询用户的 权限 包括菜单 按钮
    Set<Permission> findUserPermissionByName(@Param("username") String username,@Param("type") Integer type) ;
    // 查询全部权限
    Set<Permission> sets();
}
