package com.qiuwei.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiuwei.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-09-19.
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
