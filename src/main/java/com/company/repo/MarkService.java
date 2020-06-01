package com.company.repo;

import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<StudentMark> findAllMarksBySubjectAndStudent(String studentLogin, String subjectName) {

        CustomUser student = userRepository.findByLogin(studentLogin);
        Subject subject = subjectRepository.findByName(subjectName);

        return markRepository.findMarksByStudentAndSubject(student, subject);
    }

    @Transactional(readOnly = true)
    public StudentMark findByID(Long id) {
        return markRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addMark(int mark, String studentLogin, String subjectName, Date date){

        CustomUser student = userRepository.findByLogin(studentLogin);
        Subject subject = subjectRepository.findByName(subjectName);

        StudentMark newMark = new StudentMark(student, subject, mark, date);
        student.addMark(newMark);
        markRepository.save(newMark);
    }

    @Transactional
    public void deleteMark(CustomUser student, Date date){
        markRepository.deleteByDateAndStudent(student, date);
        student.deleteMark(markRepository.findByDateAndStudent(student, date));
    }
}
