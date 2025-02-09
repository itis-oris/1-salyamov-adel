package com.example.dao;

import com.example.models.Assignment;
import com.example.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {
    private final DBConnection instance = DBConnection.getInstance();

    // Получить все задания курса
    public List<Assignment> getAssignmentsByCourseId(int courseId) {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments WHERE course_id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Assignment assignment = new Assignment(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("end_time"),
                        rs.getInt("status")
                );
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    // Получить задание по ID
    public Assignment getAssignmentById(int assignmentId) {
        Assignment assignment = null;
        String sql = "SELECT * FROM assignments WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assignmentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                assignment = new Assignment(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("course_id"),
                        rs.getTimestamp("end_time"),
                        rs.getInt("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignment;
    }
}