package com.lee.training.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication注解包含了以下三个注解：
 * @SpringBootConfiguration：Spring基于java的配置
 * @EnableAutoConfiguration：启用自动配置
 * @ComponentScan：启用组件扫描
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.lee.training.springboot.dao"})
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
