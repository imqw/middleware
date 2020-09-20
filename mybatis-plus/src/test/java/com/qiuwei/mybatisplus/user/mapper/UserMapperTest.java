package com.qiuwei.mybatisplus.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiuwei.mybatisplus.user.entity.User;
import com.qiuwei.mybatisplus.user.enums.SexEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-09-19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }


    @Test
    public void save() {

        //插入
        User user = new User();
        user.setAge(18);
        user.setName("小红");
        user.setEmail("wcbc0310@126.com");
        user.setSex(SexEnum.WOMEN);
        userMapper.insert(user);

        //查询
        Long id = user.getId();
        User userInsert = this.userMapper.selectById(id);
        System.out.println(userInsert.toString());

        //更新

        user.setAge(27);
        user.setName("更新后的姓名");
        user.setEmail("qqqq@123.com");
        this.userMapper.updateById(user);
        System.out.println(user.toString());

        //删除
        this.userMapper.deleteById(id);

    }


    @Test
    public void del() {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age", 12).isNotNull("email").eq("name","小红");
        this.userMapper.delete(queryWrapper);

        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);


    }


    @Test
    public void page() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);


        QueryWrapper<User> params = new QueryWrapper<>();
        params.ge("age",22);
        Integer count = this.userMapper.selectCount(params);
        System.out.println(count);

        if(count>0){
            Page<User> userPage = new Page<>();
            userPage.setSize(10);
            userPage.setCurrent(1);
            Page<User> userPage1 = this.userMapper.selectPage(userPage, params);
            userPage1.getRecords().forEach(System.out::println);

        }
    }


    @Test
    public void pageUser() {
        Page<User> userPage = new Page<>();
        userPage.setSize(10);
        userPage.setCurrent(1);

        Page<User> resoult = this.userMapper.pageUser(userPage, 1);

        resoult.getRecords().forEach(System.out::println);


    }
}