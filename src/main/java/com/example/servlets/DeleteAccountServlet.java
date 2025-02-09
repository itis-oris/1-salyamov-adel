package com.example.servlets;

import com.example.models.User;
import com.example.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteAccount")
public class DeleteAccountServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            int userId = user.getId();
            try {
                userService.deleteUser(userId);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Ошибка при удалении аккаунта");
                request.getRequestDispatcher("profile.jsp").forward(request, response);
                return;
            }
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}