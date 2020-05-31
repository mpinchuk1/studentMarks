package com.company.controller;

import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.Subject;
import com.company.repo.MarkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class MarkController {

    private static final Logger logger = LoggerFactory.getLogger(MarkController.class);

    @Autowired
    private MarkService markService;

    @RequestMapping(value = "/getStudentMarksBySubject")
    public List<StudentMark> getStudentMarksBySubject(@RequestParam String studentName,
                                                      @RequestParam String subjectName){

        List<StudentMark> marks = markService.findAllMarksBySubjectAndStudent(studentName, subjectName);

        logger.info(String.valueOf(marks));
        return marks;
    }

//    @RequestMapping(value = "/addNewMark")
//    public List<StudentMark> getStudentMarksBySubject(@RequestParam String studentName,
//                                                      @RequestParam String subjectName){
//
//        List<StudentMark> marks = markService.findAllMarksBySubjectAndStudent(studentName, subjectName);
//
//        logger.info(String.valueOf(marks));
//        return marks;
//    }
}
