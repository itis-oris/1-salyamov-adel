package com.example.servlets;


import com.example.models.Assignment;
import com.example.models.Enrollment;
import com.example.models.User;
import com.example.models.Course;
import com.example.services.AssignmentService;
import com.example.services.CourseService;

import com.example.services.EnrollmentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private AssignmentService assignmentService;

    @Override
    public void init() {
        courseService = new CourseService();
        enrollmentService = new EnrollmentService();
        assignmentService = new AssignmentService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listCourses(request, response);
                break;
            case "view":
                viewCourse(request, response);
                break;
            case "view-assignment":
                showAssignment(request, response);
                break;
            case "view-my-courses":
                viewMyCourses(request, response);
                break;
            case "view-assignments":
                viewAssignments(request, response);
                break;
        }
    }

    private void listCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        String userRole = null;
        if (user != null) {
            userRole = user.getRole();
        }

        List<Course> allCourses = courseService.getAllCourses();

        List<Course> filteredCourses = new ArrayList<>(allCourses);
        if (user != null) {
            try {
                List<Enrollment> userEnrollments = enrollmentService.getEnrollmentsByStudentId(user.getId());

                Set<Integer> enrolledCourseIds = userEnrollments.stream()
                        .map(Enrollment::getCourseId)
                        .collect(Collectors.toSet());

                filteredCourses = allCourses.stream()
                        .filter(course -> !enrolledCourseIds.contains(course.getId()))
                        .collect(Collectors.toList());

            } catch (SQLException e) {
                throw new ServletException("Failed to retrieve user enrollments", e);
            }
        }

        request.setAttribute("userRole", userRole);
        request.setAttribute("courses", filteredCourses);

        request.getRequestDispatcher("jsp/courseList.jsp").forward(request, response);
    }

    private void viewCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int courseId = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.getCourseById(courseId);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String userEmail = user.getEmail();
        String userRole = user.getRole();

        request.setAttribute("course", course);
        request.setAttribute("userEmail", userEmail);
        request.setAttribute("userRole", userRole);
        request.getRequestDispatcher("jsp/course.jsp").forward(request, response);
    }

    private void viewMyCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Проверяем, авторизован ли пользователь
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Получаем пользователя из сессии
        User user = (User) session.getAttribute("user");

        try {
            // Получаем все подписки пользователя
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(user.getId());

            // Получаем информацию о курсах, на которые подписан пользователь
            List<Course> myCourses = new ArrayList<>();
            for (Enrollment enrollment : enrollments) {
                Course course = courseService.getCourseById(enrollment.getCourseId());
                if (course != null) {
                    myCourses.add(course);
                }
            }

            // Передаем список курсов в JSP
            request.setAttribute("myCourses", myCourses);
            request.getRequestDispatcher("/jsp/myCourses.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve user's courses", e);
        }
    }

    private void showAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String assignmentId = request.getParameter("id");

        Assignment assignment = assignmentService.getAssignmentById(Integer.parseInt(assignmentId));
        if (assignment == null) {
            response.sendRedirect("/courses");
            return;
        }

        request.setAttribute("assignment", assignment);
        request.getRequestDispatcher("/jsp/assignmentDetails.jsp").forward(request, response);
    }

    private void viewAssignments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получаем параметр courseId из запроса
        String courseIdParam = request.getParameter("id");

        if (courseIdParam == null || courseIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        int courseId = Integer.parseInt(courseIdParam);

        // Получаем курс по courseId
        Course course = courseService.getCourseById(courseId);
        if (course == null) {
            response.sendRedirect(request.getContextPath() + "/courses");
            return;
        }

        // Получаем список заданий курса
        List<Assignment> assignments = assignmentService.getAssignmentsByCourseId(courseId);

        // Передаем данные в JSP
        request.setAttribute("course", course);
        request.setAttribute("assignments", assignments);
        request.getRequestDispatcher("/jsp/courseAssignments.jsp").forward(request, response);

    }
}
