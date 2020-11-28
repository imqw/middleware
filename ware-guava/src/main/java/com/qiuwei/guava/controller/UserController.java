package com.qiuwei.guava.controller;

import com.qiuwei.guava.bean.User;
import com.qiuwei.guava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-11-28.
 */
@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("add")
    public String add(){
        User user = new User();
        user.setId(1L);
        user.setName("小A");
        user.setAge("18");
        user.setRemark("这是个风骚的少年...");
        this.userService.add(user);
        return "ok";
    }


    @GetMapping("get")
    public User get(Long id){
        return userService.get(id);
    }


    @DeleteMapping("del")
    public String del(Long id){
        this.userService.del(id);
        return "ok";
    }
}
