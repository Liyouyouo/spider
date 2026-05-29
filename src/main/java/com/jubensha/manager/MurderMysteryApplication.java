package com.jubensha.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jubensha.manager.dao")
public class MurderMysteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MurderMysteryApplication.class, args);
    }
}
