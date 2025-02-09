package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private String title;
    private String description;
    private String subjectName;
    private String teacherName;
    private String teacherEmail;
}