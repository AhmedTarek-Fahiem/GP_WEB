<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
</head>
<body>

<div align="center">
    <h1>New/Edit Employee</h1>
    <form:form action="saveMedicine" method="post" modelAttribute="medicine">
        <table>
            <form:hidden path="id"/>
            <tr>
                <td>Name:</td>
                <td><form:input path="name" /></td>
            </tr>
            <tr>
                <td>Category:</td>
                <td><form:input path="category" /></td>
            </tr>
            <tr>
                <td>Form:</td>
                <td><form:input path="form" /></td>
            </tr>
            <tr>
                <td>Active Ingredients:</td>
                <td><form:input path="active_ingredients" /></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><form:input path="price" /></td>
            </tr>
            <tr>
                <td>Quantity:</td>
                <td><form:input path="quantity" /></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>

</body>
</html>
