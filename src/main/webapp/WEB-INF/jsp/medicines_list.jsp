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
</head>
<body>

<div align="center">
    <h1>Medicine List</h1>
    <h3>
        <a href="/admin/newMedicine">New Medicine</a>
    </h3>
    <table border="1">

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
                <td><a href="/admin/editMedicine/${medicine.id}">Edit</a>
                    <a href="/admin/deleteMedicine/${medicine.id}">Delete</a></td>

            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
