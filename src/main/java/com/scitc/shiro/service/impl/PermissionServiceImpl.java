package com.scitc.shiro.service.impl;

import com.scitc.shiro.mapper.PermissionMapper;
import com.scitc.shiro.pojo.Permission;
import com.scitc.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Set<Permission> sets() {
        return permissionMapper.sets();
    }

    @Override
    public Set<Permission> findUserPermissionByName(String username,Integer type) {
        return permissionMapper.findUserPermissionByName(username,type);
    }

    @Override
    public Set<String> permissionUrls() {
        System.out.println("permissionUrls --------------- 判断是否有权限 ");
        Set<Permission> sets = sets();
        Set<String> urlSet = sets.stream().map(p -> p.getUrl()).filter(p -> !StringUtils.isEmpty(p)).collect(Collectors.toSet());
        return urlSet;
    }

    @Override
    public Set<String> permissionUrlsByUserName(String username) {
        // 返回用户的Url
        System.out.println("permissionUrlsByUserName --------------- 返回用户的Url ");
        Set<Permission> permissions = findUserPermissionByName(username, null);
        Set<String> urlSet = permissions.stream().map(p -> p.getUrl()).filter(p -> !StringUtils.isEmpty(p)).collect(Collectors.toSet());
        return urlSet;
    }
}
