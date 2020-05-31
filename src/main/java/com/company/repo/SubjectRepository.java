package com.company.repo;

import com.company.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    @Query("SELECT r FROM Subject r where r.name = :name")
    Subject findByName(@Param("name") String name);

}
