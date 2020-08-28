package com.qiuwei.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 *
 * @Author: qiuweib@yonyou.com
 * @Date: 2020-08-28.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {


        /**
         * 根据注解ApiOperation过滤需要生成的API接口
         */

        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName("类别一").apiInfo(apiInfo())
                .select()
                //RequestHandlerSelectors.basePackage("com") 扫描对应包下面的接口
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilder());
        return docket;
    }

    @Bean
    public Docket createRestApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName("类别二").apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilder());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("书本项目API")
                //描述
                .description("这个是书本项目相关接口文档")
                //条款地址
                .termsOfServiceUrl("http://baidau.com")
                //版本
                .version("1.0")
                .build();
    }


    /**
     * 添加定制化的请求额外参数 如请求头 包含token 可以不配置直接去掉就行
     * @return
     */
    public List<Parameter> parameterBuilder() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("tokenId").description("票").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }



}
