package com.xiaoma;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Slf4j                        log
//@ServletComponentScan         扫描servlet和filter
//@SpringBootApplication        springboot启动类注解
//@EnableTransactionManagement  事务控制注解DishServiceImpl进行多表操作,开启事务注解支持
@Slf4j
@ServletComponentScan
@EnableTransactionManagement
@SpringBootApplication
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功...");
    }
}
