package com.hard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hard.dao")
public class CommunityApplication {

    public static void main (String[] args) {
        SpringApplication.run (CommunityApplication.class, args);
    }

}
