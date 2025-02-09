<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="/static/css/login.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="login-container">
    <h2>Login to Your Account</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">
                ${errorMessage}
        </div>
    </c:if>

    <form method="post">

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required placeholder="Enter your email">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required placeholder="Enter your password">
        </div>

        <div class="form-group">
            <button type="submit" class="btn">Login</button>
        </div>
    </form>

    <p>Don't have an account? <a href="register">Register here</a></p>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
