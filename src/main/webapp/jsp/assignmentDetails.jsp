<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Задание: ${assignment.title}</title>
    <link rel="stylesheet" href="/static/css/assignmentDetails.css">
    <link rel="stylesheet" href="/static/css/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
    <h1>Задание: ${assignment.title}</h1>
    <p><strong>Описание:</strong> ${assignment.description}</p>
    <p><strong>Срок сдачи:</strong> ${assignment.endTime}</p>
    <p><strong>Статус:</strong> ${assignment.status == 1 ? 'Активно' : 'Завершено'}</p>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>