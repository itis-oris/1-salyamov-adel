package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {
    private int id;
    private String title;
    private String description;
    private int courseId;
    private Timestamp endTime;
    private int status;
}