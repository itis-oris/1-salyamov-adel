package com.example.services;

import com.example.dao.AssignmentDAO;
import com.example.models.Assignment;

import java.util.List;

public class AssignmentService {
    private final AssignmentDAO assignmentDAO;

    public AssignmentService() {
        this.assignmentDAO = new AssignmentDAO();
    }

    // Получить все задания курса
    public List<Assignment> getAssignmentsByCourseId(int courseId) {
        return assignmentDAO.getAssignmentsByCourseId(courseId);
    }

    // Получить задание по ID
    public Assignment getAssignmentById(int assignmentId) {
        return assignmentDAO.getAssignmentById(assignmentId);
    }
}