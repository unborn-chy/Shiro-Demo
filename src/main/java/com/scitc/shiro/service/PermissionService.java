package com.scitc.shiro.service;

import com.scitc.shiro.pojo.Permission;



import java.util.Set;

public interface PermissionService {
    Set<Permission> findUserPermissionByName(String username,Integer type) ;
    Set<Permission> sets() ;
    Set<String> permissionUrls() ;
    Set<String> permissionUrlsByUserName(String username);
}
