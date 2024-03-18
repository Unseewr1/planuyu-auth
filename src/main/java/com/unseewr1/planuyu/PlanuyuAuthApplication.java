package com.unseewr1.planuyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.unseewr1.planuyu.repo")
@SpringBootApplication
@ComponentScan(basePackages = {"com.unseewr1.planuyu.*"})
public class PlanuyuAuthApplication extends SpringApplication {

    public static void main(String[] args) {
        run(PlanuyuAuthApplication.class);
    }
}
