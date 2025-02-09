package com.example.dao;

import com.example.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.utils.DBConnection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDAO {
    private final DBConnection instance = DBConnection.getInstance();

    public User getUserByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection conn = instance.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
                if (bCrypt.matches(password, rs.getString("password"))) {
                    user = new User(rs.getInt("id"),
                            rs.getString("name"),
                            email,
                            rs.getString("avatar"),
                            rs.getString("bio"),
                            rs.getString("password"),
                            getRoleNameById(rs.getInt("role_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getRoleNameById(int roleId) {
        String roleName = null;
        try (Connection conn = instance.getConnection()) {
            String query = "SELECT name FROM roles WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                roleName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public String getEmailById(int id) {
        String email = null;
        try (Connection conn = instance.getConnection()) {
            String query = "SELECT email FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET avatar = ?, bio = ? WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getAvatar());
            stmt.setString(2, user.getBio());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        }
    }

    public int getRoleIdByName(String roleName) {
        int roleId = -1;
        try (Connection conn = instance.getConnection()) {
            String query = "SELECT id FROM roles WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                roleId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleId;
    }

    public boolean saveUser(User user) {
        if (isEmailRegistered(user.getEmail())) {
            return false;
        }
        try (Connection conn = instance.getConnection()) {
            String query = "INSERT INTO users (name, email, password, role_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, getRoleIdByName(user.getRole()));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailRegistered(String email) {
        try (Connection conn = instance.getConnection()) {
            String query = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserNameById(int teacherId) {
        String teacherName = null;
        String sql = "SELECT name FROM users WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teacherId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                teacherName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teacherName;
    }

    public void deleteUserById(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = instance.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}
