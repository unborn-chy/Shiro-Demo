package com.scitc.shiro.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**

 继续上面的知识点，因为URLPathMatchingFilter 没有被声明为@Bean,
 那么换句话说 URLPathMatchingFilter 就没有被Spring管理起来，那么也就无法在里面注入 PermissionService类了。
 但是在业务上URLPathMatchingFilter 里面又必须使用PermissionService类，怎么办呢? 就借助SpringContextUtils 这个工具类，来获取PermissionService的实例。
 这里提供工具类，下个步骤讲解如何使用这个工具类。
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtil.context = context;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
