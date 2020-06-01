package com.company.config;

import com.company.entities.CustomUser;
import com.company.repo.MarkService;
import com.company.repo.SubjectService;
import com.company.repo.UserService;
import com.company.repo.VisitService;
import com.company.utils.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Configuration
public class AppConfig {

    public static final String ADMIN = "admin";
    public static final String ADMINPASS = "admin";
    private Calendar c = Calendar.getInstance();

    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final MarkService markService,
                                  final VisitService visitService,
                                  final SubjectService subjectService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {

                userService.addUser(ADMIN, ADMINPASS, "Alex", "Frolov",
                        "here", "male", new Date(), UserRole.ADMIN);
                userService.addUser("user", "user", "Dima", "Pupkin",
                        "where", "male", new Date(), UserRole.USER);
                userService.addUser("usera", "usera", "Oleg", "Pupich",
                        "there", "male", new Date(), UserRole.USER);
                userService.addUser("nastya", "nastya", "Nastya", "Dobrodiy",
                        "home", "female", new Date(), UserRole.USER);
                userService.addUser("tanya", "tanya", "Tanya", "Pospeshnaya",
                        "home1", "female", new Date(3242390894L+60000000000L), UserRole.USER);
                userService.addUser("andr", "andr", "Andrew", "Darialov",
                        "home2", "male", new Date(3242390894L+60000000000L), UserRole.USER);
                userService.addUser("anton", "anton", "Anton", "Brajnii",
                        "home3", "male", new Date(3242390894L+60000008000L), UserRole.USER);
                subjectService.addSubject("Math", ADMIN);
                subjectService.addSubject("Biology", ADMIN);
                subjectService.addSubject("Programing", ADMIN);

                for (int i = 0; i < 5; i++) {
                    c.set(2020, 4+i, 4);
                    markService.addMark(5, "nastya", "Math", c.getTime());
                    visitService.addVisit(true, "nastya", "Math", c.getTime());
                }
                for (int i = 0; i < 5; i++) {
                    c.set(2020, Calendar.MAY+i, 4);
                    markService.addMark(4, "nastya", "Programing", c.getTime());
                    visitService.addVisit(false, "nastya", "Programing", c.getTime());
                }
                for (int i = 0; i < 5; i++) {
                    c.set(2020, Calendar.MAY+i, 4);
                    markService.addMark(2, "anton", "Math", c.getTime());
                    visitService.addVisit(true, "anton", "Math", c.getTime());
                }

                for (int i = 0; i < 5; i++) {
                    c.set(2020, Calendar.MAY, 4);
                    markService.addMark(15, "usera", "Math", c.getTime());
                    visitService.addVisit(false, "usera", "Math", c.getTime());
                }

                for (int i = 0; i < 5; i++) {
                    c.set(2020, Calendar.JULY+i, 4);
                    markService.addMark(65, "andr", "Math", c.getTime());
                    visitService.addVisit(true, "andr", "Math", c.getTime());
                }

                for (int i = 0; i < 5; i++) {
                    c.set(2020, Calendar.APRIL+i, 4);
                    markService.addMark(2, "anton", "Math", c.getTime());
                    visitService.addVisit(true, "anton", "Math", c.getTime());
                }

                for (int i = 0; i < 3; i++) {
                    c.set(2020, Calendar.APRIL, 2+i*3);
                    markService.addMark(3, "tanya", "Biology", c.getTime());
                    visitService.addVisit(true, "tanya", "Biology", c.getTime());
                }
                for (int i = 0; i < 4; i++) {
                    c.set(2020, Calendar.APRIL, 2+i*3);
                    markService.addMark(3, "nastya", "Biology", c.getTime());
                    visitService.addVisit(false, "nastya", "Biology", c.getTime());
                }

            }
        };
    }
}
