package com.june.blog.admin.controller;

import com.june.blog.admin.entity.BlogUser;
import com.june.blog.admin.service.BlogUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (BlogUser)表控制层
 *
 * @author makejava
 * @since 2020-11-19 10:35:53
 */
@RestController
@RequestMapping("blogUser")
public class BlogUserController {
    /**
     * 服务对象
     */
    @Resource
    private BlogUserService blogUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public BlogUser selectOne(String id) {
        return this.blogUserService.queryById(id);
    }

}
