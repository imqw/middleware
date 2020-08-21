package com.qiuwei.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiuwei.mybatisplus.entity.MpUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2020-01-21.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MpUserServiceTest {
    @Autowired
    private MpUserService mpUserService;

    @Test
    public void test1() {

        // 插入新记录
        MpUser mpUser = new MpUser();
        mpUser.setId(1L);
        mpUser.setOpenid("openId");
        mpUser.setAddress("广东深圳");
        mpUser.setUsername("David Hong");
        mpUserService.save(mpUser);
        // 或者
        mpUser.insertOrUpdate();
        // 更新完成后，mpUser对象的id会被补全

        System.out.println("插入...");
        System.out.println(mpUser.toString());

        mpUser = mpUserService.getById(1);

        System.out.println("id查询...");
        System.out.println(mpUser.toString());


    }


    @Test
    public void getId() {
        // 通过主键id查询
        MpUser mpUser = mpUserService.getById(1);
        System.out.println("id查询...");
        System.out.println(mpUser.toString());
    }


    @Test
    public void queryByParams() {

        // 条件查询，下面相当于xml中的 select * from mp_user where address = '"广东深圳' and username = 'David Hong' limit 1
        MpUser  mpUser = mpUserService.getOne(new QueryWrapper<MpUser>().eq("address", "广东深圳").eq("username", "David Hong").last("limit 1"));

        System.out.println(mpUser.toString());
    }

    @Test
    public void list() {
        // 批量查询
        List<MpUser> mpUserList = mpUserService.list();
        System.out.println(mpUserList.toString());
    }


    @Test
    public void page() {

        // 分页查询
        int pageNum = 1;
        int pageSize = 10;
        IPage<MpUser> mpUserIPage = mpUserService.page(new Page<>(pageNum, pageSize), new QueryWrapper<MpUser>().eq("openid", "openId"));
        // IPage to List
        List<MpUser> mpUserList1 = mpUserIPage.getRecords();
        // 总页数
        long allPageNum = mpUserIPage.getPages();

        System.out.println(mpUserList1.toString());
        System.out.println(allPageNum);

    }

    @Test
    public void update() {
        MpUser mpUser = new MpUser();
        mpUser.setId(1L);
        mpUser.setOpenid("openId");
        mpUser.setUsername("David Hong");

        // 修改更新
        mpUser.setAddress("广东广州");
        mpUserService.updateById(mpUser);
        // 或者
        mpUser.insertOrUpdate();

    }


    @Test
    public void remove() {
        MpUser mpUser = new MpUser();
        mpUser.setId(1L);
        // 通过主键id删除
//        mpUserService.removeById(1);
        // 或者
        mpUser.deleteById();
    }
}