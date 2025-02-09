package com.example.servlets;

import com.example.models.Course;
import com.example.models.User;
import com.example.services.CourseService;
import com.example.services.EnrollmentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/enroll")
public class EnrollServlet extends HttpServlet {
    private EnrollmentService enrollmentService;
    private CourseService courseService;
    final static Logger logger = LogManager.getLogger(EnrollServlet.class);

    @Override
    public void init() {
        enrollmentService = new EnrollmentService();
        courseService = new CourseService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String courseId = req.getParameter("courseId");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            logger.warn("Unauthorized access to /enroll. Redirecting to login.");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        Course course = courseService.getCourseById(Integer.parseInt(courseId));
        if (course == null) {
            logger.warn("Course with ID {} not found.", courseId);
            resp.sendRedirect("/courses");
            return;
        }

        try {
            if (enrollmentService.isEnrolled(user.getId(), course.getId())) {
                logger.info("User {} is already enrolled in course {}.", user.getEmail(), course.getId());
                resp.sendRedirect("/course?action=view-assignments&id=" + course.getId());
                return;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            enrollmentService.addEnrollment(user.getId(), course.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        logger.info("User {} enrolled in course {}.", user.getEmail(), course.getId());

        resp.sendRedirect("/course?action=view-assignments&id=" + course.getId());
    }
}