<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Available Courses</title>
    <link rel="stylesheet" href="/static/css/courseList.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />

<h2>Доступные курсы</h2>

<div class="course-table">
    <c:forEach var="course" items="${courses}">
        <div class="course-card">
            <h2>${course.title}</h2>
            <p><strong>Предмет:</strong> ${course.subjectName}</p>
            <p><strong>Преподаватель:</strong> ${course.teacherName}</p>
            <a href="course?action=view&id=${course.id}" class="btn-view">Подробнее</a>
        </div>
    </c:forEach>
</div>

<c:if test="${userRole == 'Преподаватель'}">
    <a href="course?action=add" class="add-course-btn">Add New Course</a>
</c:if>

<jsp:include page="footer.jsp" />
</body>
</html>