package com.example.services;

import com.example.dao.CourseDAO;
import com.example.models.Course;

import java.util.List;

public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService() {
        courseDAO = new CourseDAO();
    }

    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }

    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    public void deleteCourse(int courseId) {
        courseDAO.deleteCourse(courseId);
    }

    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}
