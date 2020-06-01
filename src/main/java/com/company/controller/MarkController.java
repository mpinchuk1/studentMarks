package com.company.controller;

import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.StudentVisit;
import com.company.entities.Subject;
import com.company.repo.MarkService;
import com.company.repo.SubjectService;
import com.company.repo.UserService;
import com.company.repo.VisitService;
import com.company.utils.StudentMarkDTO;
import com.company.utils.StudentVisitDTO;
import com.company.utils.UserRole;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


@RestController
public class MarkController {

    private static final Logger logger = LoggerFactory.getLogger(MarkController.class);

    @Autowired
    private MarkService markService;
    @Autowired
    private UserService userService;
    @Autowired
    private VisitService visitService;
    @Autowired
    private SubjectService subjectService;


    @RequestMapping(value = "/getStudentsInfoBySubject")
    public List<CustomUser> getStudentMarksBySubject(@RequestParam String subjectName){
        List<CustomUser> usersList = userService.findUsersByRole(UserRole.USER);
        usersList.forEach(user -> {
            user.setMarks(markService.findAllMarksBySubjectAndStudent(user.getLogin(), subjectName));
            user.setVisits(visitService.findAllVisitsBySubjectAndStudent(user.getLogin(), subjectName));
        });

        logger.info(String.valueOf(usersList));
        return usersList;
    }

        @RequestMapping(value = "/updateMarkTable", method = RequestMethod.POST)
    public ResponseEntity<Void> updateMarks(
            @RequestParam String marksJson,
            @RequestParam String subject) {

            //System.out.println(subject + marksJson);
            StudentMarkDTO[] marks = new Gson().fromJson(marksJson, StudentMarkDTO[].class);
            System.out.println(Arrays.toString(marks));
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");

            Date date = new Date();
            for (StudentMarkDTO studentMarkDTO : marks) {
                if (studentMarkDTO.getValue() != null) {
                    try {
                        date = format.parse(studentMarkDTO.getDate());
                        System.out.println(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    markService.addMark(Integer.parseInt(studentMarkDTO.getValue()),
                            userService.findByID(Long.valueOf(studentMarkDTO.getStudent_id())).getLogin(), subject, date);
                } else {
                    markService.deleteMark(userService.findByID(Long.valueOf(studentMarkDTO.getStudent_id())), date);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

       return ResponseEntity.ok().build();

    }

    @RequestMapping(value = "/updateVisitTable", method = RequestMethod.POST)
    public ResponseEntity<Void> updateVisits(
            @RequestParam(name = "visits") String visitsJson,
            @RequestParam String subject) {
        StudentVisitDTO[] visits = new Gson().fromJson(visitsJson, StudentVisitDTO[].class);
        System.out.println(Arrays.toString(visits));
        try {
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");

            Date date = new Date();
            for (StudentVisitDTO studentVisitDTO : visits) {
                if (studentVisitDTO.getValue() != null) {
                    try {
                        date = format.parse(studentVisitDTO.getDate());
                        System.out.println(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    visitService.addVisit(Boolean.parseBoolean(studentVisitDTO.getValue()),
                            userService.findByID(Long.valueOf(studentVisitDTO.getStudent_id())).getLogin(), subject, date);
                } else {
                    visitService.deleteVisit(userService.findByID(Long.valueOf(studentVisitDTO.getStudent_id())), date);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();

    }

}
