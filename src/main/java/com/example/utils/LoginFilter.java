package com.example.utils;

import com.example.models.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/login";
        String registerURI = httpRequest.getContextPath() + "/register";
        String courseURI = httpRequest.getContextPath() + "/course";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean registerRequest = httpRequest.getRequestURI().equals(registerURI);
        boolean courseRequest = httpRequest.getRequestURI().equals(courseURI) && httpRequest.getQueryString() == null;
        boolean stat = httpRequest.getRequestURI().startsWith("/static/");

        if (loggedIn || loginRequest || registerRequest || stat || courseRequest) {
            String userRole = null;
            if (session != null) {
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    userRole = user.getRole();
                }
            }

            String action = httpRequest.getParameter("action");

            if (action != null && requiresTeacherRole(action) && !"Преподаватель".equals(userRole)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/course?action=list&error=access_denied");
                return;
            }

            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    private boolean requiresTeacherRole(String action) {
        return action.equals("add") || action.equals("edit") || action.equals("delete") || action.equals("update");
    }

    @Override
    public void destroy() {
    }
}

