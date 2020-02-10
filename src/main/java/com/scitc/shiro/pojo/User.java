package com.scitc.shiro.pojo;


import lombok.Data;

import java.util.Set;

@Data
public class User {
    private Integer id;
    private String  username;
    private String  password;
    private String  salt;
    private Set<Role> roleSet;


    public String getCredentialsSalt(){
        return salt + username + username;
    }
}
