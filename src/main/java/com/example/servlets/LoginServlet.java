package com.example.servlets;

import com.example.models.User;
import com.example.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    final static Logger logger = LogManager.getLogger(LoginServlet.class);

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            logger.warn("Reauthorization attempt");
            resp.sendRedirect("/profile");
        } else {
            logger.info("GET request to /login");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        logger.info("Attempting login for email: {}", email);

        User user = userService.authenticateUser(email, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            logger.info("User logged in successfully: {}", user.getEmail());
            resp.sendRedirect("/profile");
        } else {
            logger.warn("Login failed for email: {}", email);
            req.setAttribute("errorMessage", "Неверный email или пароль");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }

    }
}
