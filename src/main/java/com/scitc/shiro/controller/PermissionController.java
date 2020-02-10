package com.scitc.shiro.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController

public class PermissionController {

    @GetMapping("/article/add")
    public String add() {
        return "添加文章";
    }

    @GetMapping("/article/update")
    public String update() {
        return "修改文章";
    }
    @GetMapping("/article/delete")
    public String delete() {
        return "删除文章";
    }

    // 用户管理
    @GetMapping("/user/list")
    public String list() {
        return "用户列表";
    }

    @GetMapping("/user/delete")
    public String userDel() {
        return "删除用户";
    }


}
