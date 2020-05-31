package com.company.repo;

import com.company.entities.CustomUser;
import com.company.entities.StudentMark;
import com.company.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MarkRepository extends JpaRepository<StudentMark, Long> {

    @Query("SELECT m FROM StudentMark m WHERE m.student = :student and m.subject = :subject order by m.date")
    List<StudentMark> findMarksByStudentAndSubject(@Param("student")CustomUser student,
                                                  @Param("subject")Subject subject);

}
