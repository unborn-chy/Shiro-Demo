package com.scitc.shiro.shiro;

import com.scitc.shiro.service.PermissionService;
import com.scitc.shiro.utils.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;


/***
 * 需要注意一点，URLPathMatchingFilter 并没有用@Bean管理起来。
 * 原因是Shiro的bug, 这个也是过滤器，ShiroFilterFactoryBean 也是过滤器，当他们都出现的时候，默认的什么anno,authc,logout过滤器就失效了。
 * 所以不能把他声明为@Bean。
 *
 * 我的功能
 *          默认不需要登录
 * 判断请求的 url 是否 需要权限
 *              不需要 放行
 *              需要  判断登录用户中是否有该权限
 *                  有 放行
 *                  没有 拦截
 */
public class URLPathMatchingFilter extends PathMatchingFilter {
    @Autowired
    private PermissionService permissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        //请求的url
        String requestURL = getPathWithinApplication(request);
//        System.out.println("请求的url :" + requestURL);
        Subject subject = SecurityUtils.getSubject();


        if (permissionService == null) {
            permissionService = SpringContextUtil.getContext().getBean(PermissionService.class);
        }
        Set<String> urls = permissionService.permissionUrls();

        // 判断该Url是否需要权限
        if (!urls.contains(requestURL)) {
            return true; //不包括
        }

        if (!subject.isAuthenticated()) {
            // 如果没有登录, 进入登录流程
            WebUtils.issueRedirect(request, response, "/login");
            return false;
        }
        // 判断用户是否有这个权限
        Set<String> myUrls = (Set<String>) subject.getSession().getAttribute("permissionUrls");
        if (myUrls.contains(requestURL)) {
            return true;
        }
        WebUtils.issueRedirect(request, response, "/unauthorized");
        return false;

    }
}
