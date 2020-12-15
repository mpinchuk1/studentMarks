package com.company.config;


import com.company.utils.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Date;

@Configuration
public class AppConfig {

    public static final String ADMIN = "admin";
    public static final String ADMINPASS = "admin";
    private Calendar c = Calendar.getInstance();

    @Bean
    public CommandLineRunner demo() {
        return strings -> {


        };
    }
}
