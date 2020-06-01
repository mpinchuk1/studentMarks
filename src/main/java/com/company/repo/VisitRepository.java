package com.company.repo;


import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.StudentVisit;
import com.company.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VisitRepository extends JpaRepository<StudentVisit, Long> {

    @Query("SELECT v FROM StudentVisit v WHERE v.student = :student and v.subject = :subject order by v.date")
    List<StudentVisit> findVisitsByStudentAndSubject(@Param("student") CustomUser student,
                                                   @Param("subject") Subject subject);
    @Modifying
    @Query("DELETE FROM StudentVisit v WHERE v.student = :student and v.date = :date")
    void deleteByDateAndStudent(@Param("student") CustomUser student,
                                @Param("date") Date date);

    @Query("SELECT v FROM StudentVisit v WHERE v.student = :student and v.date = :date")
    StudentVisit findByDateAndStudent(@Param("student") CustomUser student,
                                @Param("date") Date date);
}
