<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Профиль</title>
    <link rel="stylesheet" href="/static/css/profile.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="profile-container">
    <h1>Профиль</h1>
    <c:choose>
        <c:when test="${not empty user.avatar}">
            <img src="/static/avatars/${user.avatar}" alt="Avatar" class="profile-avatar">
        </c:when>
        <c:otherwise>
            <img src="/static/avatars/default_avatar.jpg" alt="Default Avatar" class="profile-avatar">
        </c:otherwise>
    </c:choose>
    <p>Имя: ${user.name}</p>
    <p>Почта: ${user.email}</p>
    <p>Биография: ${user.bio}</p>
    <p>Роль: ${user.role}</p>

    <!-- Форма для изменения данных -->
    <form action="profile" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="updateProfile">

        <!-- Загрузка аватарки -->
        <div>
            <label for="avatar">Загрузить аватарку:</label>
            <input type="file" id="avatar" name="avatar" accept="image/*">
        </div>

        <!-- Изменение биографии -->
        <div>
            <label for="bio">Биография:</label>
            <textarea id="bio" name="bio" rows="4" cols="50"></textarea>
        </div>

        <!-- Кнопка отправки -->
        <button type="submit" class="update-button">Сохранить изменения</button>
    </form>

    <!-- Кнопка выхода -->
    <form action="logout" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="logout-button">Logout</button>
    </form>

    <!-- Кнопка удаления аккаунта -->
    <form action="deleteAccount" method="post">
        <input type="hidden" name="action" value="deleteAccount">
        <button type="submit" class="delete-account-button" onclick="return confirm('Вы уверены, что хотите удалить аккаунт?')">Удалить аккаунт</button>
    </form>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>