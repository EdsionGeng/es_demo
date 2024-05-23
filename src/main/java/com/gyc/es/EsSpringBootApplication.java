package com.gyc.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = {"com.gyc.es"})
public class EsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsSpringBootApplication.class, args);
    }

}
