package com.june.blog.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * (BlogUser)实体类
 *
 * @author makejava
 * @since 2020-11-19 10:35:53
 */
@Data
@ApiModel("$tableInfo.comment")
public class BlogUser implements Serializable {
    private static final long serialVersionUID = 785646346851168479L;
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

}
