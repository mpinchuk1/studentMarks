package com.company.utils;

import com.company.entities.CustomUser;
import com.company.entities.Subject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

public class StudentMarkDTO {

    private String student_id;
    private String date;
    private String value;
    @Autowired
    public StudentMarkDTO(){

    }

    public StudentMarkDTO(String student_id, String date, String value){
        this.student_id = student_id;
        this.date = date;
        this.value = value;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StudentMarkDTO{" +
                "student_id='" + student_id + '\'' +
                ", date='" + date + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
