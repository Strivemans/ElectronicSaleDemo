package com.jack.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {"com.jack.demo"})
@EnableSwagger2 //要在 启动类添加 swagger-ui的 启动
//该注解 可以自动扫描该包下面的全部注解,需要把包准确扫描到 mapper类所在的包 .mapper
@MapperScan(basePackages ="com.jack.demo.mapper")
public class ElectronicSaleDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicSaleDemoApplication.class, args);
	}

}
