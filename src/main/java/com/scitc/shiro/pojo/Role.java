package com.scitc.shiro.pojo;

import lombok.Data;

import java.util.Set;

@Data
public class Role {
    private Integer id;
    private String role;
    private String description;

    Set<Permission> permissionSet;
}
