package com.company.repo;

import com.company.config.AppConfig;
import com.company.entities.CustomUser;
import com.company.utils.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubjectService subjectService;

    public UserService(UserRepository userRepository, SubjectService subjectService) {
        this.userRepository = userRepository;
        this.subjectService = subjectService;
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional(readOnly = true)
    public CustomUser findByID(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<CustomUser> findUsersByRole(UserRole role){
        return userRepository.findCustomUsersByRole(role);
    }

    @Transactional
    public boolean addUser(String login, String password, String name, String surname, String address,
                           String sex, Date birthday, UserRole role) {

        CustomUser user = new CustomUser(login, password, name, surname, address, sex, birthday, role);
        subjectService.findAllSubjects().forEach(s -> s.addUserToSubject(user));
        userRepository.save(user);
        return true;
    }

}
