package com.company.controller;

import com.company.entities.Subject;
import com.company.repo.SubjectService;
import com.company.repo.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class SubjectController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private UserService userService;

    @RequestMapping("/subjects")
    public ModelAndView subjectsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("subjects");
        return modelAndView;
    }

    @RequestMapping("/getSubjects")
    public List<Subject> getSubjects(){
        return subjectService.findAllSubjects();
    }

    @RequestMapping("/getSubject")
    public Subject getSubject(@RequestParam String name){
        return subjectService.findByName(name);
    }


    @RequestMapping("/selectedSubject")
    public ModelAndView selectedSubjectsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("selectedSubject");
        return modelAndView;
    }

}
