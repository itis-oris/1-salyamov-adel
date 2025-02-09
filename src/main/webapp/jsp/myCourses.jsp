<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои курсы</title>
    <link rel="stylesheet" href="/static/css/myCourses.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />

<h2>Мои курсы</h2>

<div class="course-table">
    <c:forEach var="course" items="${myCourses}">
        <div class="course-card">
            <h2>${course.title}</h2>
            <p><strong>Предмет:</strong> ${course.subjectName}</p>
            <p><strong>Преподаватель:</strong> ${course.teacherName}</p>
            <a href="/course?action=view-assignments&id=${course.id}" class="btn-view">Подробнее</a>
        </div>
    </c:forEach>
</div>

<c:if test="${empty myCourses}">
    <p class="no-courses">Вы пока не подписаны ни на один курс.</p>
</c:if>

<jsp:include page="footer.jsp" />
</body>
</html>