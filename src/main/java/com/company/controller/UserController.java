package com.company.controller;

import com.company.entities.CustomUser;
import com.company.repo.SubjectService;
import com.company.repo.UserService;
import com.company.utils.CustomUserDTO;
import com.company.utils.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/getStudents")
    public List<CustomUserDTO> getStudents() {

        List<CustomUserDTO> usersList = new ArrayList<>();
        userService.findUsersByRole(UserRole.USER).forEach(u ->
                usersList.add(new CustomUserDTO(u.getId(), u.getName(), u.getSurname(), u.getAddress(),
                        u.getSex(), u.getBirthday(), u.getRole())));

        return usersList;
    }

    @RequestMapping("/getUser")
    public CustomUser getUserById(@RequestParam String id){
        return userService.findByID(Long.valueOf(id));
    }


    @RequestMapping("/")
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping("/login")
    public ResponseEntity<Void> loginPage(@RequestParam String login,
                                          @RequestParam String password) {
        AtomicBoolean flag = new AtomicBoolean(false);
        userService.getAllUsers().forEach(user -> {
            if(user.getLogin().equals(login) && user.getPassword().equals(password))
                flag.set(true);
        });

        if(flag.get()) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

}
