package com.qiuwei.mybatisplus.user.mapper;

import com.qiuwei.mybatisplus.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiuwei
 * @since 2020-09-19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
