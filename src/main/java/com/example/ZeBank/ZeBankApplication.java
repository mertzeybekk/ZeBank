package com.example.ZeBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example.ZeBank.Controller","com.example.ZeBank.Util.PasswordUtil",  "com.example.ZeBank.Service", "com.example.ZeBank.Config","com.example.ZeBank.FilterLayer","com.example.ZeBank.Producer",
        "com.example.ZeBank.Consumer","com.example.ZeBank.Email","com.example.ZeBank.MailConfig","com.example.ZeBank.Util.SmsSendUtil"})

public class ZeBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZeBankApplication.class, args);
    }

}
