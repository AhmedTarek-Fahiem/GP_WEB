<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 18-Apr-18
  Time: 6:54 PM
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

    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css"/>" rel="stylesheet">

    <link href="<c:url value="/resources/vendor/jquery/css/jquery-ui.min.css"/>" rel="stylesheet">

    <title>Prescription QR</title>
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
                        <a class="nav-link js-scroll-trigger" href="/access_patient">Access New Patient</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link js-scroll-trigger" href="<c:url value="/logout" />">Logout</a>
                    </li>

                </ul>
            </div>
        </div>
    </nav>


    <section class="masthead" id="qr_section">
        <div class="container h-100">
            <div class="row h-100">
                    <div class="col-lg-12 my-auto">
                        <div class="header-content mx-auto">
                            <br>
                            <br>
                            <br>
                            <c:if test="${not empty prescription_qr}">
                                <h5 id="label">Prescription QR</h5>
                                <img src="data:image/png;base64,${prescription_qr}" alt="Prescription QR" width="400" height="400">
                            </c:if>
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


    <script defer src="<c:url value="/resources/vendor/font-awesome/js/fontawesome-all.min.js"/>"></script>

    <script defer src="<c:url value="/resources/vendor/jquery/js/jquery-ui.min.js"/>"></script>

    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>

    <!-- Plugin JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>

    <!-- Custom scripts for this template -->
    <script src="<c:url value="/resources/js/new-age.min.js" />"></script>

</body>
</html>
