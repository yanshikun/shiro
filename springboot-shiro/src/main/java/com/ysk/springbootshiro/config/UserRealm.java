package com.ysk.springbootshiro.config;

import com.ysk.springbootshiro.pojo.User;
import com.ysk.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//翻译：领域
//创建 realm  继承 AuthorizingRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //------------------>授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权-》principalCollection");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //得到当前用户管理
        Subject subject = SecurityUtils.getSubject();
        //得到当前登陆的用户
        User user = (User) subject.getPrincipal();
        //将从数据库中的用户权限，给shiro来管理
        info.addStringPermission(user.getPerms());

        return info;
    }

    //------------------->认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证-》authenticationToken");

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.queryUserByName(token.getUsername());
        if(user==null){
            return null;//返回空值默认抛出UnknownAccountException用户不存在一场
        }
        //将数据库查询出的密码传入，即会和token中的密码进行比较,shiro帮做密码验证
        //arg0 将登陆的用户放入PreviousPrincipals
        return new SimpleAccount(user,user.getPassword(),"");
    }
}
