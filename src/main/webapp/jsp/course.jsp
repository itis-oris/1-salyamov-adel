<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Details</title>
    <link rel="stylesheet" href="/static/css/course.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />
<div class="course-details">
    <h2>${course.title}</h2>
    <p><strong>Предмет:</strong> ${course.subjectName}</p>
    <p><strong>Преподаватель:</strong> ${course.teacherName}</p>
    <p><strong>Описание:</strong> ${course.description}</p>

    <c:choose>
        <c:when test="${userRole == 'Студент'}">
            <a href="/enroll?courseId=${course.id}" class="button">Subscribe</a>
        </c:when>
        <c:when test="${userEmail == course.teacherEmail}">
            <a href="/editCourse?courseId=${course.id}" class="button edit-button">Edit Course</a>
        </c:when>
        <c:otherwise>
            <p>У вас нет прав на редактирование и подписку</p>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>