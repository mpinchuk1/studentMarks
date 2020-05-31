package com.company.config;

import com.company.entities.CustomUser;
import com.company.repo.SubjectService;
import com.company.repo.UserService;
import com.company.utils.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class AppConfig {

    public static final String ADMIN = "admin";
    public static final String ADMINPASS = "admin";


    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final SubjectService subjectService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.addUser(ADMIN, ADMINPASS, "Alex", "Frolov",
                        "here", "male", new Date(), UserRole.ADMIN);
                userService.addUser("user", "user", "Dima", "Pupkin",
                        "here", "male", new Date(), UserRole.USER);
                subjectService.addSubject("Math", ADMIN);
                subjectService.addSubject("Biology", ADMIN);
                subjectService.addSubject("Programing", ADMIN);

                for (int i = 0; i < 10; i++) {
                    userService.addUser("user" + i, "user" + i, "Name" + i, "Surname" + i,
                            "here", "male", new Date(), UserRole.USER);
                }

            }
        };
    }
}
