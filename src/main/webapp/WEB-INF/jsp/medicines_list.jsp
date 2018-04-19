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
    <title>Home Page</title>
</head>
<body>

<div align="center">
    <h1>Medicine List</h1>
    <table border="1">

        <th>Medicine ID</th>
        <th>Medicine Name</th>
        <th>Medicine Category</th>
        <th>Medicine Form</th>
        <th>Medicine Active Ingredients</th>
        <th>Medicine Price</th>
        <th>Medicine Quantity</th>

        <c:forEach var="medicine" items="${listMedicine}">
            <tr>

                <td>${medicine.id}</td>
                <td>${medicine.name}</td>
                <td>${medicine.category}</td>
                <td>${medicine.form}</td>
                <td>${medicine.active_ingredients}</td>
                <td>${medicine.price}</td>
                <td>${medicine.quantity}</td>

            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
