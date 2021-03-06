<%@taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 22-Mar-18
  Time: 6:38 AM
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

    <title>Login</title>

    <link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">

    <link rel="stylesheet" href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>">

    <!-- Custom fonts for this template -->
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Catamaran:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Muli" rel="stylesheet">

    <link href="<c:url value="/resources/css/app.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet">

</head>

<body>

    <section class="wrapper">
        <div class="col-lg-8 col-lg-offset-2">
                <h1 class="text-center">Login</h1>

                <div class="login-form">
                    <c:url var="loginUrl" value="/login" />
                    <form action="${loginUrl}" method="post" class="form-horizontal">
                        <c:if test="${param.error != null}">
                            <div class="alert alert-danger">
                                <p>Invalid username and password</p>
                            </div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="alert alert-success">
                                <p>You have been logged out successfully</p>
                            </div>
                        </c:if>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                            <input type="text" class="form-control login-input" id="username" name="username" placeholder="Username" required>
                        </div>
                        <div class="input-group input-sm">
                            <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
                            <input type="password" class="form-control login-input" id="password" name="password" placeholder="Password" required>
                        </div>
                        <div>
                            <div class="checkbox text-center">
                                <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />

                        <div class="form-actions">
                            <button type="submit" class="center-block">Log in</button>
                        </div>
                    </form>
                </div>

                <p>This login is intended for doctors only. If you are a doctor and wish to join, send us your information to Elixir.me.Ltd@gmail.com</p>

        </div>

    </section>
    <footer>
        <div>
            <p>&copy; Elixir 2018. All Rights Reserved</p>
        </div>
    </footer>

    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="https://code.jquery.com/jquery-3.2.1.min.js" />"></script>
    <script src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js" />"></script>


</body>

</html>
