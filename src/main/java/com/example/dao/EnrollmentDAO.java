package com.example.dao;

import com.example.models.Enrollment;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    private final DBConnection instance = DBConnection.getInstance();

    public void addEnrollment(int studentId, int courseId) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isEnrolled(int studentId, int courseId) throws SQLException {
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";
        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Enrollment enrollment = new Enrollment(
                            resultSet.getInt("id"),
                            resultSet.getInt("student_id"),
                            resultSet.getInt("course_id"),
                            resultSet.getTimestamp("enrollment_date")
                    );
                    enrollments.add(enrollment);
                }
            }
        }
        return enrollments;
    }

}
