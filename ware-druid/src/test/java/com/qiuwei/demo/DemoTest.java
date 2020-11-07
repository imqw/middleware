package com.qiuwei.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2020-04-13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
    public static String sRet = "";

    @Test
    public void name() {

        func(1);
        func(2);
        System.out.println(sRet);

    }


    public static void func(int i) {
        try {
            if (i % 2 == 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            sRet += "0";
            return;
        } finally {
            sRet += "1";
        }
        sRet += "2";
    }


    @Test
    public void demo1() {




    }
}
