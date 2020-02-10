package com.scitc.shiro.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Permission {
    private Integer id;
    private String  name;
    private String url;
    // 类型 菜单还是按钮
    private Integer type;
    private Integer parentId;

    private List<Permission> children =  new ArrayList<>();
}
