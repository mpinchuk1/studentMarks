package com.company.utils;

import com.company.entities.CustomUser;
import com.company.entities.Subject;

import java.util.Date;

public class StudentVisitDTO {

    private String student_id;
    private String date;
    private String value;

    public StudentVisitDTO(){

    }

    public StudentVisitDTO(String student_id, String date, String value){
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
}
