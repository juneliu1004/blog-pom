package com.june.blog.admin.service.impl;

import com.june.blog.admin.entity.SysUser;
import com.june.blog.admin.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-11-18 09:42:48
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService, UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //SysUser user = sysUserDao.queryById(Long.parseLong(username));
        SysUser user = new SysUser();
        user.setName(username);
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));

        return user;
    }
}
