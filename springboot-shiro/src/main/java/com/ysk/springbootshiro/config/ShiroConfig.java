package com.ysk.springbootshiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //三个核心对象
    //1.ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //添加安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加shiro内置过滤器
        /*
        * anon : 无需认证即可访问
        * authc : 必须认证才可访问
        * user :   必须拥有记住我功能才可访问
        * parma : 拥有对某个资源的权限才能访问
        * role : 拥有某个角色权限才可访问
        * */
        Map<String,String> filterMap = new LinkedHashMap<>();
        //进入此请求，需要admin用户的add权限才可访问
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        filterMap.put("/user/*","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        // 登陆界面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");

        //成功登陆后的界面
        shiroFilterFactoryBean.setSuccessUrl("/toIndex");

        // 跳转未授权 ，没有权限访问的界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


        return shiroFilterFactoryBean;
    }
    //2.DefaultSecurityManager对象
    @Bean(name="securityManager")
    public DefaultSecurityManager defaultSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
        //此处返回值应为DefaultSecurityManager的子类DefaultWebSecurityManager-------<
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    //3. 创建 Rrealm对象  继承AuthorizingRealm
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //shiro整合thymeleafo配置
    //ShiroDialect
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
