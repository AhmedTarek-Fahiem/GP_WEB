<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
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

    <link href="<c:url value="/resources/css/history.css" />" rel="stylesheet">

    <link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">

    <title>Patient Profile</title>

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
                        <a class="nav-link js-scroll-trigger" href="#current_history">Current History</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="#new_prescription">New Prescription</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="/access">Access History</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value="/logout" />">Logout</a>
                    </li>

                </ul>
            </div>
        </div>
        </div>
    </nav>

    <section class="masthead" id="current_history">
        <div class="container h-75">
            <div class="row h-100">
                <div class="col-lg-12 my-auto">
                    <div class="header-content mx-auto">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading" align="center"><span class="lead" style="font-weight: bold; font-size: large">Current Medical History </span></div>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Medicines</th>
                                    <th>Description</th>

                                </tr>
                                </thead>
                                <tbody>
                                <c:choose>
                                    <c:when test="${empty patient_details}">
                                        <tr>
                                            <td>---</td>
                                            <td>---</td>
                                            <td>---</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${patient_details}" var="prescription" >
                                            <tr>
                                                <td>${prescription.prescription_date}</td>
                                                <td>
                                                    <div align="left">
                                                        <ul>
                                                            <c:forEach items="${prescription.medicines}" var="medicine">
                                                                <li>${medicine.name}</li>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td align="left">${prescription.prescription_description}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                </tbody>
                            </table>
                            <c:if test="${not empty fetch_details_error}">
                                <div class="alert alert-danger">
                                    <p align="center">${fetch_details_error}</p>
                                </div>
                            </c:if>
                        </div>
                        <a class="nav-link js-scroll-trigger btn" href="#new_prescription">New Prescription</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="masthead" id="new_prescription">
        <div class="col-lg-12 my-auto">
            <div class="header-content mx-auto">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <br>
                        <h1>Welcome</h1>
                        <br/>
                        <input class="typeahead form-control" style="margin:0px auto;width:300px;" type="text" placeholder="Medicine name">
                    </div>
                </div>
                <br><br>
                <label class="col-form-label-lg" for="medicines">Current Prescription</label>
                <table class="table" id="medicines">
                    <tr>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Quantity</th>
                    </tr>
                    <tr>
                        <td>Viagra</td>
                        <td>Pills</td>
                        <td>4</td>
                    </tr>
                </table>
                <a class="nav-link js-scroll-trigger btn" href="#history_description">Write Prescription History</a>
            </div>
        </div>
    </section>

    <section class="masthead" id="history_description">
        <div class="container h-100">
            <div class="row h-100">
                <div class="col-lg-7 my-auto">
                    <div class="header-content mx-auto">
                        <form action="/access">
                            <div class="form-group">
                                <label class="col-form-label-lg" for="add_history">Add to History</label>
                                <textarea class="form-control" id="add_history" rows="3"  placeholder="New case to add to history..." style="max-height: 200px"></textarea>
                            </div>
                            <button class="btn" type="submit" >Confirm</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; Graduation 2018. All Rights Reserved</p>
        </div>
    </footer>


    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">

    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>

    <!-- Plugin JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>

    <!-- Custom scripts for this template -->
    <script src="<c:url value="/resources/js/new-age.min.js" />"></script>

    <script src="<c:url value="/resources/js/typeahead.js" />"></script>

    <script type="text/javascript">

        $("input.typeahead").typeahead({
            source:  function (query, process) {
                return $.get('http://localhost:8080/api/getMedicinesList', {  }, function (data) {
                    console.log(data);
                    data = $.parseJSON(data);
                    return process(data);
                });
            }
        });

    </script>

</body>

</html>
