<%@taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page isELIgnored="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 07-Apr-18
  Time: 4:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">

<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Access Denied</title>

    <link href="<c:url value="/resources/css/app.css"/>" rel="stylesheet">

</head>

<body>

    <div class="generic-container">
        <div class="authbar">
            <span>Dear <strong>${logged_in_user}</strong>, You are not authorized to access this page.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
        </div>
    </div>

</body>

</html>
