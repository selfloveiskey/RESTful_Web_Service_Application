package com.restful.restful_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RestfulApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RestfulApplication.class, args);
        System.out.println("Testing github");
    }
}