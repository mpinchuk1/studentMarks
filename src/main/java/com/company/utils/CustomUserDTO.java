package com.company.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomUserDTO {

    private Long id;
    private String name;
    private String surname;
    private String address;
    private String sex;
    private String birthday;
    private UserRole role;
    private final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public CustomUserDTO() {

    }

    public CustomUserDTO(Long id, String name, String surname, String address,
                         String sex, Date birthday, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.sex = sex;
        this.role = role;
        this.birthday = this.dateFormat.format(birthday);
    }

    @Override
    public String toString() {
        return "CustomUserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", role=" + role +
                ", dateFormat=" + dateFormat +
                '}';
    }
}
