package com.qiuwei.guava.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.qiuwei.guava.bean.User;
import com.qiuwei.guava.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-11-28.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final static Map<Long, User> USER_DB=new HashMap<>();

    @Autowired
    Cache<Long, Object> caffeineCache;

    @Override
    public void add(User user) {
        USER_DB.put(user.getId(),user);
    }

    @Override
    public User get(Long userId) {
        User result=null;

        this.caffeineCache.getIfPresent(userId);
        ConcurrentMap<Long, Object> map = this.caffeineCache.asMap();
        Object obj = map.get(userId);
        if(obj!=null){
            result=(User)obj;
            log.info("从缓存中获取...");
            return result;
        }
        result = USER_DB.get(userId);
        log.info("从数据库获取...");
        if(result!=null){
            this.caffeineCache.put(userId,result);
        }
        return result;
    }

    @Override
    public void del(Long userId) {
        USER_DB.remove(userId);
        caffeineCache.cleanUp();

    }
}
