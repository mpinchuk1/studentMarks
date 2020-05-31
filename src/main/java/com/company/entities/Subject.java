package com.company.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonManagedReference
    private CustomUser teacher;
    @ManyToMany(mappedBy = "subjects")
    private List<CustomUser> subjectMembers = new ArrayList<>();

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public void addUserToSubject(CustomUser student){
        subjectMembers.add(student);
        student.getSubjects().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomUser getTeacher() {
        return teacher;
    }

    public void setTeacher(CustomUser teacher) {
        this.teacher = teacher;
    }

    public List<CustomUser> getSubjectMembers() {
        return subjectMembers;
    }

    public void setSubjectMembers(List<CustomUser> subjectMembers) {
        this.subjectMembers = subjectMembers;
    }
}
