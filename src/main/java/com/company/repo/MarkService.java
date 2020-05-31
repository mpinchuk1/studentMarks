package com.company.repo;

import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<StudentMark> findAllMarksBySubjectAndStudent(String studentt, String subjectt) {

        CustomUser student = userRepository.findByLogin(studentt);
        Subject subject = subjectRepository.findByName(subjectt);

        return markRepository.findMarksByStudentAndSubject(student, subject);
    }

    @Transactional(readOnly = true)
    public StudentMark findByID(Long id) {
        return markRepository.findById(id).orElse(null);
    }

    @Transactional
    public boolean addMark(int mark, String studentName, String subjectName, Date date){

        CustomUser student = userRepository.findByLogin(studentName);
        Subject subject = subjectRepository.findByName(subjectName);

        StudentMark newMark = new StudentMark(student, subject, mark, date);
        markRepository.save(newMark);
        return true;
    }
}
