package com.qiuwei.mybatisplus;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * mybatis-plus配置类
 * @Author: qiuwei@19pay.com.cn
 * @Date: 2020-01-21.
 */
@Configuration
@Slf4j
public class MyBatisPlusConfig {

    /**
     *  配置分页插件
     *
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.debug("注册分页插件");
        return new PaginationInterceptor();
    }



    /**
     *  SQL执行效率插件
     *  设置 dev test 环境开启
     *
     */
    @Bean
    @Profile({"test"})
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }


    /**
     * 逻辑删除用，3.1.1之后的版本可不需要配置该bean，但项目这里用的是3.1.0的
     *
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }


}
