<%@ page import="com.graduationproject.model.Patient" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

    <link href="<c:url value="/resources/css/cart.css" />" rel="stylesheet">

    <link href="<c:url value="/resources/vendor/jquery/css/jquery-ui.css"/>" rel="stylesheet">

    <title>
        <%
            if ((boolean) session.getAttribute("registered_patient"))
                    out.print(((Patient)session.getAttribute("patient")).getUsername() + "'s ");
        %>Cart
    </title>

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
                        <a class="nav-link js-scroll-trigger" href="/access_patient">Access another Patient</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value="/logout" />">Logout</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>

    <section class="masthead" id="prescription">
        <div class="container h-100">
            <div class="row h-100">
                <div class="col-lg-12 my-auto">
                    <div class="header-content mx-auto">

                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading" align="center"><span class="lead" style="font-weight: bold; font-size: large">Current Prescription </span></div>
                            <br>
                            <div id="load_me"><jsp:include page="cart_table.jsp"/></div>
                        </div>
                <c:choose>
                    <c:when test="${not empty history}">
                        <input type="submit" class="btn btn-block btn-info" id="commit_prescription" value="Commit" />
                    </c:when>
                    <c:otherwise>
                        <form action="/commit_cart" method="post">
                            <input type="submit" class="btn btn-block btn-info" value="Confirm" />
                        </form>
                    </c:otherwise>
                </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <c:if test="${not empty history}">
    <section class="masthead" id="history_section">
        <div class="col-lg-12 my-auto">
            <div class="header-content mx-auto">
                <div class="col-md-12 text-center">
                    <form:form action="/commit_cart" method="post" modelAttribute="history" >

                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label class="col-form-label-lg" for="history_description" style="font-weight: bold; font-size: large">Add to History</label>
                            <br>
                            <form:textarea type="text" class="form-control" id="history_description" path="description" rows="3" cols="5" placeholder="New case to add to history..." style="max-height: 200px;" required="true"/>
                        </div>
                        <input type="submit" id="submit_prescription" value="Confirm" class="btn btn-block btn-info" >

                    </form:form>
                    <div class="error_response">
                        <c:if test="${not empty commit_error}">
                            <div class="alert alert-danger">
                                <h1 align="center">${commit_error}</h1>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </section>
    </c:if>

    <footer>
        <div class="container">
            <p>&copy; Graduation 2018. All Rights Reserved</p>
        </div>
    </footer>


    <script defer src="<c:url value="/resources/vendor/font-awesome/js/fontawesome-all.min.js"/>"></script>

    <script defer src="<c:url value="/resources/vendor/jquery/js/jquery-ui.js"/>"></script>


    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>

    <!-- Plugin JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>

    <!-- Custom scripts for this template -->
    <script src="<c:url value="/resources/js/new-age.min.js" />"></script>


    <script type="text/javascript">

        var reloadData = 0;

        $(function() {

            $("#history_section").hide();
            loadData();

            $("#commit_prescription").click(function () {
                <c:if test="${not empty history}">
                    $("#history_section").show();
                    $('html, body').animate({
                        scrollTop: $("#history_section").offset().top
                    }, 500);
                </c:if>
            });
        });

        function setMedicineQuantity(medicine_id, quantity) {
            $.post("<c:url value="/edit_medicine_quantity"/>" + "?medicine_id=" + medicine_id + "&quantity=" + quantity)
                .done(function (data, status, xhr) {
                    if (data === "Done")
                        console.log("success editing");
                    else if (data === "Error")
                        alert("Error in editing the medicine quantity.");
                    else if (data === "Out_of_Stock")
                        alert("Sorry This medicine is not currently in stock.");
                    loadData();
                });
        }
        function deleteMedicine(medicine_name, medicine_id) {
            var r = confirm('Are you sure you want to delete \n' + medicine_name + ' ?');
            if (r === true) {
                $.post("<c:url value="/delete_medicine"/>" + "?medicine_id=" + medicine_id)
                    .done(function (data, status, xhr) {
                        console.log("success deleting");
                        if (document.getElementById("prescription_medicines").getElementsByTagName("tr").length === 1)
                            history.back();
                        else
                            loadData();
                    });
            }
        }

        function loadData() {
            $('#load_me').load("cart_table", function() {
                if (reloadData !== 0)
                    window.clearTimeout(reloadData);
                reloadData = window.setTimeout(loadData, 10000)
            }).fadeIn("slow");
        }

    </script>

</body>

</html>
