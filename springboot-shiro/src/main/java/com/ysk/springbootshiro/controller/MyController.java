package com.ysk.springbootshiro.controller;

import com.ysk.springbootshiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello!shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String toAdd(Model model){
        return "user/add";
    }
    @RequestMapping("/user/update")
    public String toUpdate(){
        return "user/update";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(Model model,String username,String password){
        //得到当前用户管理
        Subject subject = SecurityUtils.getSubject();
        //用户登陆的 封装为 令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //是否记住我
        token.setRememberMe(false);
        //会执行AuthorizingRealm->doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        //进行用户判断
        try {
            subject.login(token);
            return "index";
        }catch (UnknownAccountException uae){//用户不存在
            model.addAttribute("msg","用户不存在！");
            return "login";
        }catch (IncorrectCredentialsException ice){//密码不存在
            model.addAttribute("msg","密码不正确！");
            return "login";
        }
    }

    /**
     * 未授权要跳转的请求
     * @return
     */
    @RequestMapping("/unauthorized")
    @ResponseBody
    public String unauthorized(){
        return "sorry!你没有足够的权限访问到这里！";
    }

    /**
     * 本地登出页面
     * @param
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return "index";
    }
}
