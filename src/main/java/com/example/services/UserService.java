package com.example.services;

import com.example.dao.UserDAO;
import com.example.models.User;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public User authenticateUser(String email, String password) {
        return userDAO.getUserByEmailAndPassword(email, password);
    }

    public boolean registerUser(User user) {
        return userDAO.saveUser(user);
    }

    public void deleteUser(int userId) throws SQLException {
        userDAO.deleteUserById(userId);
    }
}
