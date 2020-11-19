package com.june.blog.admin.controller;

import com.june.blog.admin.entity.SysUser;
import com.june.blog.admin.service.SysUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-11-18 09:42:48
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUser selectOne(Long id) {

        rabbitTemplate.convertAndSend("adminQueue","你好 admin-ui");

        return new SysUser();
    }

}
