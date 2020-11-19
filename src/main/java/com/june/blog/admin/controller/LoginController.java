package com.june.blog.admin.controller;

import com.june.blog.admin.entity.SysUser;
import com.june.blog.admin.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @PostMapping("login")
    public String login(@RequestBody SysUser user){
        System.out.println(user.getName());
        System.out.println(user.getPassword());


        return "111";
    }

    @PostMapping("registry")
    public String registry(@RequestBody SysUser user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return "成功";
    }

    @GetMapping("user")
    public SysUser getUser(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        SysUser user = new SysUser();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            user.setName(authentication.getName());
        }



        return user;

    }


}
