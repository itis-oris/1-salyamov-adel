package com.example.servlets;

import com.example.dao.UserDAO;
import com.example.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet("/profile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ProfileServlet extends HttpServlet {
    final static Logger logger = LogManager.getLogger(ProfileServlet.class);
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("GET request to /profile");

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            logger.info("User accessed profile: {}", user.getEmail());

            req.setAttribute("user", user);
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
        } else {
            logger.warn("Unauthorized access attempt to /profile");
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");

        if ("updateProfile".equals(action)) {
            // Обработка загрузки аватарки
            Part filePart = req.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/") + "static/avatars/";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                String filePath = uploadPath + user.getId() + "_" + fileName;
                filePart.write(filePath);

                // Обновляем путь к аватарке в пользователе
                user.setAvatar(user.getId() + "_" + fileName);
            }

            // Обработка изменения биографии
            String bio = req.getParameter("bio");
            if (bio != null) {
                user.setBio(bio);
            }

            // Сохраняем изменения в базе данных
            try {
                userDAO.updateUser(user);
                logger.info("User {} updated profile", user.getEmail());
            } catch (Exception e) {
                logger.error("Failed to update user profile", e);
                req.setAttribute("errorMessage", "Ошибка при сохранении изменений");
            }

            // Обновляем пользователя в сессии
            session.setAttribute("user", user);

            // Перенаправляем на страницу профиля
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/profile");
        }
    }
}