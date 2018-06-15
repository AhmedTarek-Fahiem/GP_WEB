<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 12-Mar-18
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>New Medicine</title>

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/cart.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/vendor/jquery/css/jquery-ui.css"/>">
</head>
<body id="page-top">
<nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="#page-top">Cart</a>
        <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            Menu
            <i class="fa fa-bars"></i>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="<c:url value="/logout" />">Logout</a>
                </li>

            </ul>
        </div>
    </div>
</nav>
<section class="masthead">
    <div class="row h-100">
        <div class="col-lg-12 my-auto text-center">
            <div align="center">
                <h1>New/Edit Medicine</h1>
                <form:form action="/admin/saveMedicine" method="post" modelAttribute="medicine">
                    <table class="table-hover table-striped">
                        <form:hidden path="id"/>
                        <tr>
                            <td>Name:</td>
                            <td><form:input class="form-control" path="name" required="true" /></td>
                        </tr>
                        <tr>
                            <td>Category:</td>
                            <td><form:input class="form-control" path="category" required="true" /></td>
                        </tr>
                        <tr>
                            <td>Form:</td>
                            <td><form:input class="form-control" path="form" required="true" /></td>
                        </tr>
                        <tr>
                            <td>Active Ingredients:</td>
                            <td><form:input class="form-control" path="active_ingredients" required="true" /></td>
                        </tr>
                        <tr>
                            <td>Price:</td>
                            <td><form:input class="form-control" path="price" type="number" min="1" required="true" /></td>
                        </tr>
                        <tr>
                            <td>Quantity:</td>
                            <td><form:input class="form-control" path="quantity" type="number" min="0" required="true" /></td>
                        </tr>
                        <tr>
                            <td>isRestricted:</td>
                            <td><form:input class="form-control" path="isRestricted" type="number" min="0" max="1" /></td>
                        </tr>
                        <tr>
                            <td>Z:</td>
                            <td><form:input class="form-control" path="z" type="number" min="0" /></td>
                        </tr>
                        <tr>
                            <td>X:</td>
                            <td><form:input class="form-control" path="x" type="number" min="0" /></td>
                        </tr>
                        <tr>
                            <td>Y:</td>
                            <td><form:input class="form-control" path="y" type="number" min="0" /></td>
                        </tr>
                        <tr>
                            <td colspan="2" align="center"><input type="submit" class="btn btn-info" value="Save"></td>
                        </tr>
                    </table>
                </form:form>
            </div>
        </div>
    </div>
</section>
    <footer>
        <div class="container">
            <p>&copy; Elixir 2018. All Rights Reserved</p>
        </div>
    </footer>

    <script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script defer src="<c:url value="/resources/vendor/font-awesome/js/fontawesome-all.min.js"/>"></script>
    <script src="<c:url value="/resources/js/new-age.min.js" />"></script>
    <script defer src="<c:url value="/resources/vendor/jquery/js/jquery-ui.js"/>"></script>
</body>
</html>
