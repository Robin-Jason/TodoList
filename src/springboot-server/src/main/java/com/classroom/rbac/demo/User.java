package com.classroom.rbac.demo;

import lombok.Data;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String gender;
    private Date birthDate;
}

