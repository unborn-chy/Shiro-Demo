package com.scitc.shiro.shiro;

import com.scitc.shiro.pojo.Role;
import com.scitc.shiro.pojo.User;
import com.scitc.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    //权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        System.out.println("MyShiroRealm------------添加权限");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findUserRoleAndPermission(username);
        for (Role role : user.getRoleSet()) {
            // 拿到角色名称
            authorizationInfo.addRole(role.getRole());
            //拿到权限url
            role.getPermissionSet().forEach(r->  {
                if(!StringUtils.isEmpty(r.getUrl()))  {
                    authorizationInfo.addStringPermission(r.getUrl());
                }
            });
        }

        return authorizationInfo;
    }

    //身份
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
       String username =  (String)token.getPrincipal();
        User user = userService.findUserByName(username);
        if(user==null){
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user.getUsername()
                ,user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()),getName());
        return simpleAuthenticationInfo;
    }
}
