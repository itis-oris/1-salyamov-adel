<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Register</title>
  <link rel="stylesheet" href="/static/css/register.css">
  <link rel="stylesheet" href="/static/css/styles.css">
  <script src="/static/js/register.js"></script>
</head>

<body>
<jsp:include page="header.jsp" />
<div class="register-container">
  <h2>Register</h2>

  <!-- Ошибка -->
  <c:if test="${not empty errorMessage}">
    <p class="error">${errorMessage}</p>
  </c:if>

  <form method="post">

    <div class="form-group">
      <label for="username">Username</label>
      <input type="text" id="username" name="username" required>
    </div>

    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" id="password" name="password" required>
    </div>

    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" id="email" name="email" required>
    </div>

    <div class="form-group">
      <label for="role">Role</label>
      <select id="role" name="role" required onchange="handleRoleChange()">
        <option value="Студент">Студент</option>
        <option value="Преподаватель">Преподаватель</option>
      </select>
    </div>

    <!-- Поле для ввода кода преподавателя (по умолчанию скрыто) -->
    <div class="form-group" id="teacherCodeField">
      <label for="teacherCode">Введите код преподавателя</label>
      <input type="password" id="teacherCode" name="teacherCode" placeholder="">
    </div>

    <div class="form-group">
      <button type="submit">Register</button>
    </div>
  </form>

  <p style="text-align: center;">Already have an account? <a href="login">Login here</a></p>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
