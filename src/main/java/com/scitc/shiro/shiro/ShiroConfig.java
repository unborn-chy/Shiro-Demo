package com.scitc.shiro.shiro;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //打开 登录 注册
        filterChainDefinitionMap.put("/doLogin", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        // 现在已经实现 /index 页面 登录，或者 记住我 都可访问
        // 需要权限的页面还是需要登录验证
        filterChainDefinitionMap.put("/index", "user");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");


//        1). anon 可以被 匿名访问
//        2). authc 必须认证（登录）才能访问
//        3). logout 登出
//        4). roles 角色过滤器
//        5). perms 权限 过滤器


       // 写死类型
        //DefaultFilter类
//        filterChainDefinitionMap.put("/menu", "authc");
//        filterChainDefinitionMap.put("/user/**", "roles[admin]");
//        filterChainDefinitionMap.put("/**", "authc");


        //自定义拦截器  动态的Filter（url）
        // 一定要要让登录页面这些允许访问 anon
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("requestUrl",urlPathMatchingFilter());
        filterChainDefinitionMap.put("/**", "requestUrl");

        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        // 记住我
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);//散列算法  md5
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);//2
        return hashedCredentialsMatcher;
    }


    @Bean
    PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }


    public URLPathMatchingFilter urlPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    //Shiro与Spring的关联 开启利用注解配置权限

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * <p>
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    // @RequiresRoles(value={“admin”, “user”}, logical= Logical.AND)
    // 表示当前 Subject 需要角色 admin 和 user。

    // @RequiresPermissions (value={“user:a”, “user:b”}, logical= Logical.OR)
    // 表示当前 Subject 需要角色 admin 和 user。
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    // 配置rememberMe
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));

        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("M2djA70UBBUPDibGZBRvrA=="));
        manager.setCookie(rememberMeCookie());
        return manager;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);//30 天
        return simpleCookie;
    }

}
