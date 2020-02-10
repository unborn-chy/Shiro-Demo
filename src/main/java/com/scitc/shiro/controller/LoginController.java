package com.scitc.shiro.controller;

import com.scitc.shiro.enums.PermissionEnum;
import com.scitc.shiro.pojo.Permission;
import com.scitc.shiro.pojo.User;
import com.scitc.shiro.service.PermissionService;
import com.scitc.shiro.service.UserRoleService;
import com.scitc.shiro.service.UserService;
import com.scitc.shiro.shiro.PasswordHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@RestController
@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/menu")
    @ResponseBody
    public Object menu() {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        // 菜单
        Set<Permission> permissionSet = permissionService.findUserPermissionByName(username, PermissionEnum.MENU.getType());

        List<Permission> root = new ArrayList<>();
        //构建权限树
        for (Permission p : permissionSet) {
            if (p.getParentId() == 0) {
                root.add(p);
            } else {
                for (Permission permission : permissionSet) {
                    if (p.getParentId().equals(permission.getId())) {
                        permission.getChildren().add(p);
                        break;
                    }
                }
            }
        }
        return root;

    }


    @GetMapping("/test/1")
    @ResponseBody
    public Object test() {
        return "Test ";
    }


    @GetMapping("/unauthorized")
    @ResponseBody
    public String unauthorized() {
        return "没有权限 - 401 ";
    }

    @GetMapping("/login")

    public String login() {
//        return "我是登录页面，被拦截就跳转到这里";
        return "login";
    }

    @GetMapping("/index")
    @ResponseBody
    public String index() {
        return "我是主页";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "成功退出。--logout ";
    }

    @GetMapping("/doLogin")
    @ResponseBody
    public Object doLogin(@RequestParam String username, @RequestParam String password, boolean remember) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        if (remember) {
            token.setRememberMe(true);
        }
        System.out.println("remember    ----------" + remember);

        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (Exception e) {
            return new ResponseEntity<Object>("用户名/密码错误", HttpStatus.OK);
        }
        // 登录成功后把 用户的权限信息存入 session

        Set<String> urlSet = permissionService.permissionUrlsByUserName(username);
        subject.getSession().setAttribute("permissionUrls", urlSet);

        return urlSet;

    }

    //注册用户
    @GetMapping("/register")
    @ResponseBody
    public String register(@RequestParam String username, @RequestParam String password) {
        // 如果已经登录就显示
        Subject subject = SecurityUtils.getSubject();
        //如果已经登录，跳转到后台首页
        if (subject.isAuthenticated()) {
            return "你已经登录过了，请退出再注册";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        PasswordHelper.encryptPassword(user);

        userService.register(user);
        // 测试 写死
        userRoleService.save(user.getId(), 2);
        return "注册成功";
    }


    // 用来测试js递归的
    @GetMapping("/recursion")
    public String recursion() {
        return "recursion";
    }

    @GetMapping("/myPermission")
    @ResponseBody
    public Object myPermission() {
        // 菜单
        Set<Permission> permissionSet = permissionService.sets();

        List<Permission> root = new ArrayList<>();
        //构建权限树
        for (Permission p : permissionSet) {
            if (p.getParentId() == 0) {
                root.add(p);
            } else {
                for (Permission permission : permissionSet) {
                    if (p.getParentId().equals(permission.getId())) {
                        permission.getChildren().add(p);
                        break;
                    }
                }
            }
        }
        return root;

    }




}
