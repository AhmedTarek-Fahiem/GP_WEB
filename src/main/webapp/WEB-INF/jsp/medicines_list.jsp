<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 11-Mar-18
  Time: 9:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin Page</title>

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
                <h1>Medicine List</h1>
                <h3>
                    <a href="/admin/newMedicine" class="btn btn-info"><span class="fas fa-plus fa-lg remove"></span> New Medicine</a>
                </h3>
                <div class="mx-auto table-div">
                    <table class="table table-hover table-bordered table-striped">
                        <thead>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Form</th>
                            <th>Active Ingredients</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Is Restricted</th>
                            <th>Z</th>
                            <th>X</th>
                            <th>Y</th>
                            <th></th>
                        </thead>
                        <tbody>
                            <c:forEach var="medicine" items="${medicines_list}">
                                <tr>

                                    <td>${medicine.id}</td>
                                    <td style="font-weight: bold">${medicine.name}</td>
                                    <td>${medicine.category}</td>
                                    <td>${medicine.form}</td>
                                    <td>${medicine.active_ingredients}</td>
                                    <td>${medicine.price}</td>
                                    <td>${medicine.quantity}</td>
                                    <td>${medicine.isRestricted}</td>
                                    <td>${medicine.z}</td>
                                    <td>${medicine.x}</td>
                                    <td>${medicine.y}</td>
                                    <td><a href="/admin/editMedicine/${medicine.id}"><span class="fas fa-edit fa-lg remove"></span></a>
                                        <a href="/admin/deleteMedicine/${medicine.id}"><span class="fas fa-minus fa-lg remove"></span></a></td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
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
