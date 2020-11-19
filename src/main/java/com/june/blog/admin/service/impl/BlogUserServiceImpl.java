package com.june.blog.admin.service.impl;

import com.june.blog.admin.dao.BlogUserDao;
import com.june.blog.admin.entity.BlogUser;
import com.june.blog.admin.service.BlogUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (BlogUser)表服务实现类
 *
 * @author makejava
 * @since 2020-11-19 10:35:53
 */
@Service("blogUserService")
public class BlogUserServiceImpl implements BlogUserService {
    @Resource
    private BlogUserDao blogUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public BlogUser queryById(String id) {
        return this.blogUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<BlogUser> queryAllByLimit(int offset, int limit) {
        return this.blogUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param blogUser 实例对象
     * @return 实例对象
     */
    @Override
    public BlogUser insert(BlogUser blogUser) {
        this.blogUserDao.insert(blogUser);
        return blogUser;
    }

    /**
     * 修改数据
     *
     * @param blogUser 实例对象
     * @return 实例对象
     */
    @Override
    public BlogUser update(BlogUser blogUser) {
        this.blogUserDao.update(blogUser);
        return this.queryById(blogUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.blogUserDao.deleteById(id) > 0;
    }
}
