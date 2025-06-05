package com.zhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot 启动类
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.zhang.mapper") // 重要！扫描你的Mapper接口
public class MovieRecommendApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieRecommendApplication.class, args);
    }
}