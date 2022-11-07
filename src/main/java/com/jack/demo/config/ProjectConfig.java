package com.jack.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jack
 * @version 1.0
 * @date 2022/5/21 9:56
 * 项目的配置类
 */
@Configuration
@MapperScan("com.jack.demo.mapper")
public class ProjectConfig {
    //分页插件 加入该插件 mybatis-plus 可执行分页
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
