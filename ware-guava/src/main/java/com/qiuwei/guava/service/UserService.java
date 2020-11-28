package com.qiuwei.guava.service;

import com.qiuwei.guava.bean.User;

/**
 *
 *
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-11-28.
 */
public interface UserService {


    /**
     * 添加用户
     * @param user
     */
    void add(User user);


    /**
     * 获取单个用户
     * @return
     */
    User get(Long userId);


    /**
     * 删除
     */
    void del(Long userId);


}
