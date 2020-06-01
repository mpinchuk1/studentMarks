package com.company.repo;

import com.company.entities.Subject;
import com.company.entities.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Subject> findAllSubjects() {
        return subjectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Subject findByID(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addSubject(String name, String teacherLogin) {

        Subject newSubject = new Subject(name);
        CustomUser teacher = userRepository.findByLogin(teacherLogin);
        newSubject.setTeacher(teacher);

        userRepository.findAll().forEach(newSubject::addUserToSubject);

        subjectRepository.save(newSubject);
    }

    @Transactional(readOnly = true)
    public Subject findByName(String name){
        return subjectRepository.findByName(name);
    }

}
