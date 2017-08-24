package com.surpass.appwithspringboot.controller;

import com.surpass.appwithspringboot.config.security.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by surpass.wei@gmail.com on 2017/7/28.
 */
@Controller
public class DataController {

    @RequestMapping("/index")
    public String index(Model model, CasAuthenticationToken token) {
        UserInfo userDetails = (UserInfo) token.getUserDetails();
        model.addAttribute("user", userDetails);
        return "index";
    }

    @RequestMapping("/about")
    public String hello(Model model) {
        model.addAttribute("info", "这是CAS单点登录演示程序，访问这个地址不需要登录认证");
        return "index";
    }

    @RequestMapping("/user-private")
    @PreAuthorize("hasRole('USER')")    //  允许USER角色访问
    public String security(Model model) {
        model.addAttribute("info", "这是用户私人信息，仅用户自己可访问");
        return "index";
    }

    @RequestMapping("/user")
    @PreAuthorize("hasAuthority('user_read')") //  必须要有email_read权限的才能访问
    public String authorize(Model model, CasAuthenticationToken token) {
        UserInfo userDetails = (UserInfo) token.getUserDetails();
        model.addAttribute("info", userDetails.toString());
        return "index";
    }
}
