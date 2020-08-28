package com.qiuwei.swagger.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 书本
 *
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-08-28.
 */
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = -6044198948178528507L;

    @ApiModelProperty(value = "书本名称", required = false, example = "不得不说的一二三事")
    private String name;
    @ApiModelProperty(value = "书本价格", required = false, example = "100")
    private String price;
    @ApiModelProperty(value = "书本介绍", required = false, example = "这是成人书籍")
    private String desc;
    @ApiModelProperty(value = "书本类型", required = true, example = "1")
    private Integer type;

}
