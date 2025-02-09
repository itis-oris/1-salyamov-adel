package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private int id;
    private int studentId;
    private int courseId;
    private Timestamp enrollmentDate;
}