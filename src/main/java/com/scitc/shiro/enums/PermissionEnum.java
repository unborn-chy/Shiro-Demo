package com.scitc.shiro.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  PermissionEnum {
    MENU(1,"菜单"),
    BTN(2,"按钮"),;
    private Integer type;
    private String  desc;

}
