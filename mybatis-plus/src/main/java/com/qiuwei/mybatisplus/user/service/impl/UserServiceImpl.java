package com.qiuwei.mybatisplus.user.service.impl;

import com.qiuwei.mybatisplus.user.entity.User;
import com.qiuwei.mybatisplus.user.mapper.UserMapper;
import com.qiuwei.mybatisplus.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiuwei
 * @since 2020-09-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
