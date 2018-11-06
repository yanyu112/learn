package com.billow.user;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import java.lang.reflect.InvocationTargetException;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.billow")
public class AdminUserApp {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        //放入到ApplicationContext中可以在系统中使用getBean获取
        SpringContextUtil.setApplicationContext(SpringApplication.run(AdminUserApp.class, args));
    }
}
