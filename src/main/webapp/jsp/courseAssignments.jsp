<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Задания курса</title>
    <link rel="stylesheet" href="/static/css/courseAssignments.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
    <h1>Задания курса: ${course.title}</h1>
    <c:choose>
        <c:when test="${not empty assignments}">
            <ul class="assignment-list">
                <c:forEach var="assignment" items="${assignments}">
                    <li>
                        <!-- Обернуть всю информацию о задании в ссылку -->
                        <a href="/course?action=view-assignment&id=${assignment.id}" class="assignment-link">
                            <h2>${assignment.title}</h2>
                            <p><strong>Срок сдачи:</strong> ${assignment.endTime}</p>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p>У этого курса пока нет заданий.</p>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>