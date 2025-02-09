<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="header">
    <div class="header-left">
        <h1>Learnify</h1>
    </div>
    <div class="header-right">
        <a href="course" class="nav-button">Главная</a>
        <c:choose>
            <c:when test="${not empty user}">
                <a href="course?action=view-my-courses" class="nav-button">Мои курсы</a>
                <a href="profile" class="nav-button">Профиль</a>
            </c:when>
            <c:otherwise>
                <a href="register" class="nav-button">Sign Up</a>
                <a href="login" class="nav-button">Sign In</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
