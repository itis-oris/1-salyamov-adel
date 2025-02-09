package com.example.services;

import com.example.dao.EnrollmentDAO;
import com.example.models.Enrollment;

import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentDAO enrollmentDAO;

    public EnrollmentService() {
        enrollmentDAO = new EnrollmentDAO();
    }

    public void addEnrollment(int studentId, int courseId) throws SQLException {
        enrollmentDAO.addEnrollment(studentId, courseId);
    }

    public boolean isEnrolled(int studentId, int courseId) throws SQLException {
        return enrollmentDAO.isEnrolled(studentId, courseId);
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) throws SQLException {
        return enrollmentDAO.getEnrollmentsByStudentId(studentId);
    }
}
