package com.qiuwei.guava.service;

import com.qiuwei.guava.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-11-28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    public void add() {
        User user = new User();
        user.setId(1L);
        user.setName("小A");
        user.setAge("18");
        user.setRemark("这是个风骚的少年...");
        this.userService.add(user);
    }


    @Test
    public void get() {

        User user = this.userService.get(1L);

        System.out.println(user);

    }

    @Test
    public void del() {

        this.userService.del(1L);
    }
}