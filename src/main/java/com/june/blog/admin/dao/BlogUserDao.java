package com.june.blog.admin.dao;

import com.june.blog.admin.entity.BlogUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (BlogUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-19 10:35:53
 */
public interface BlogUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    BlogUser queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<BlogUser> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param blogUser 实例对象
     * @return 对象列表
     */
    List<BlogUser> queryAll(BlogUser blogUser);

    /**
     * 新增数据
     *
     * @param blogUser 实例对象
     * @return 影响行数
     */
    int insert(BlogUser blogUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<BlogUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<BlogUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<BlogUser> entities);

    /**
     * 修改数据
     *
     * @param blogUser 实例对象
     * @return 影响行数
     */
    int update(BlogUser blogUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}
