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
    import org.springframework.security.crypto.bcrypt.BCrypt;

    import java.io.IOException;

    @WebServlet("/register")
    public class RegisterServlet extends HttpServlet {
        private UserService userService;
        final static Logger logger = LogManager.getLogger(RegisterServlet.class);

        @Override
        public void init() {
            userService = new UserService();
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("user") != null) {
                logger.warn("Attempt to re-register");
                resp.sendRedirect("/profile");
            } else {
                logger.info("GET request to /register");
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String role = req.getParameter("role");
            logger.info("Attempting registration for email: {}", email);

            if ("Преподаватель".equals(role)) {
                String teacherCode = req.getParameter("teacherCode");
                if (!"prepod".equals(teacherCode)) {
                    logger.warn("Invalid teacher code provided for email: {}", email);
                    req.setAttribute("errorMessage", "Неверный код преподавателя.");
                    req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
                    return;
                }
            }

            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password, salt);

            User newUser = new User(0, username, email, null, null, hashedPassword, role);
            if (userService.registerUser(newUser)) {
                logger.info("User registered successfully: {}", email);
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                logger.warn("Registration failed: {}", email);
                req.setAttribute("errorMessage", "Registration failed. Email already in use.");
                req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            }


        }
    }
