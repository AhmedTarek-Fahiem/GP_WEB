<%@taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 31-Mar-18
  Time: 5:52 PM
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

    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

    <link href="<c:url value="/resources/css/cart.css" />" rel="stylesheet">

    <title>Patient Access</title>

</head>

<body id="page-top">

    <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand js-scroll-trigger" href="#page-top">Patient Access</a>
            <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                Menu
                <i class="fas fa-bars fa-sm"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/on_the_move_prescription">Prescription</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value="/logout" />">Logout</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>

    <section class="masthead" id="access">
        <div class="container h-100">
            <div class="row h-100">
                <div class="col-lg-10 my-auto">
                    <div class="header-content mx-auto">

                        <div class="form-group">

                            <div class="login-form">
                            <%--TODO: form action--%>
                            <form:form action="/access_patient" method="post" modelAttribute="patient" class="form" id="submitForm">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger">
                                        <p align="center">Invalid username or PIN</p>
                                    </div>
                                </c:if>
                                <form:hidden path="id"/>
                                <form:input id="username" name="username" path="username" placeholder="Enter the patient's Username" class="form-control" required="true"/><br>
                                <form:input id="pin" name="pin" path="pin" placeholder="Enter the patient's PIN" type="number" class="form-control" required="true"/><br>
                                <input type="submit" value="Access" class="btn btn-block btn-info">
                            </form:form>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; Elixir 2018. All Rights Reserved</p>
        </div>
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>


</body>

</html>
