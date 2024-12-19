package com.example.Teacher_portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
public class TeacherPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherPortalApplication.class, args);

    }

}


