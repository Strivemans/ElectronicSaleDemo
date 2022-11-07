package com.jack.demo.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jack
 * @version 1.0
 * @date 2022/5/6 11:27
 */
@Configurable
@EnableSwagger2  //开启swaggerUI
// swaggerUi 网页地址：localhost:8080/swagger-ui.html
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    //信息的描述
    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("学生管理系统API文档")
                .description("本文档描述了学生管理系统定义")
                .version("1.0")
                .contact(new Contact("jack", "http://jack.com", "1654074213@qq.com"))
                .build();
    }
}
