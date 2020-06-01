package com.company.repo;

import com.company.entities.CustomUser;
import com.company.entities.StudentVisit;
import com.company.entities.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<StudentVisit> findAllVisitsBySubjectAndStudent(String studentt, String subjectt) {

        CustomUser student = userRepository.findByLogin(studentt);
        Subject subject = subjectRepository.findByName(subjectt);

        return visitRepository.findVisitsByStudentAndSubject(student, subject);
    }

    @Transactional(readOnly = true)
    public StudentVisit findByID(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addVisit(boolean visit, String studentLogin, String subjectName, Date date){

        CustomUser student = userRepository.findByLogin(studentLogin);
        Subject subject = subjectRepository.findByName(subjectName);

        StudentVisit newVisit = new StudentVisit(student, subject, visit, date);
        student.addVisit(newVisit);
        visitRepository.save(newVisit);
    }

    public void deleteVisit(CustomUser student, Date date) {
        visitRepository.deleteByDateAndStudent(student, date);
        student.deleteVisit(visitRepository.findByDateAndStudent(student, date));
    }
}
