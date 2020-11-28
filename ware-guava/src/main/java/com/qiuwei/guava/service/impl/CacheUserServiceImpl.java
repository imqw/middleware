package com.qiuwei.guava.service.impl;

import com.qiuwei.guava.bean.User;
import com.qiuwei.guava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-11-28.
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "caffeineCacheManager")
public class CacheUserServiceImpl implements UserService {




    @Override
    @CachePut(key = "#user.id")
    public void add(User user) {

        UserServiceImpl.USER_DB.put(user.getId(),user);
    }

    @Override
    @Cacheable(key = "#userId")
    public User get(Long userId) {
        User user = UserServiceImpl.USER_DB.get(userId);
        return user;
    }

    @Override
    @CacheEvict(key = "userid")
    public void del(Long userId) {

    }
}
