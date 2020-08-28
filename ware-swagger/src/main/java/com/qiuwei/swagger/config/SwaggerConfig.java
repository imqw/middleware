package com.qiuwei.swagger.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import springfox.documentation.RequestHandler;
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

import static javax.xml.transform.OutputKeys.VERSION;

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

        //根据ApiOperation注解过滤显示的接口
        Predicate<RequestHandler> predicate = requestHandler -> {
            HandlerMethod handlerMethod = requestHandler.getHandlerMethod();
            ApiOperation methodAnnotation = handlerMethod.getMethodAnnotation(ApiOperation.class);
            if (methodAnnotation == null) {
                return false;
            }
            String value = methodAnnotation.value();
            if (value.contains(VERSION) || StringUtils.isBlank(VERSION)) {
                return true;
            }
            return false;
        };
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName("WXApi-V" + VERSION).apiInfo(apiInfo())
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilder());
        return docket;
    }

    @Bean
    public Docket createRestApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.groupName("WXApi").apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilder());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("WX Doc")
                .description("WX Doc")
                .termsOfServiceUrl("git@code.ziroom.com:rentbackend/WX.git")
                .contact("Crazy_4J@163.com")
                .version("1.0")
                .build();
    }


    public List<Parameter> parameterBuilder() {
        //添加head参数
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }



}
