package com.example.dao;

import com.example.models.Course;
import com.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    private final SubjectDAO subjectDAO = new SubjectDAO();  // DAO для subjects
    private final UserDAO userDAO = new UserDAO();
    private final DBConnection instance = DBConnection.getInstance();


    public void addCourse(Course course) {
        String sql = "INSERT INTO courses (title, description) VALUES (?, ?)";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCourse(Course course) {
        String sql = "UPDATE courses SET title = ?, description = ? WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getTitle());
            stmt.setString(2, course.getDescription());
            stmt.setInt(3, course.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        Course course = null;

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setSubjectName(subjectDAO.getSubjectNameById(rs.getInt("subject_id")));
                course.setTeacherName(userDAO.getUserNameById(rs.getInt("teacher_id")));
                course.setTeacherEmail(userDAO.getEmailById(rs.getInt("teacher_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();

        try (Connection conn = instance.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setTitle(rs.getString("title"));
                course.setDescription(rs.getString("description"));
                course.setSubjectName(subjectDAO.getSubjectNameById(rs.getInt("subject_id")));
                course.setTeacherName(userDAO.getUserNameById(rs.getInt("teacher_id")));
                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

}
