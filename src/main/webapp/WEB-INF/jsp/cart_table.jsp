<%@ page import="com.graduationproject.model.Medicine" %>
<%@ page import="java.util.List" %>
<%@ page import="com.graduationproject.model.CartMedicine" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
  Created by IntelliJ IDEA.
  User: Ahmed_Tarek
  Date: 17-Apr-18
  Time: 5:45 AM
  To change this template use File | Settings | File Templates.
--%>

<table class="table table-hover" id="medicines_table">
    <thead>
    <tr>
        <th>Name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody id="prescription_medicines">

    <%
        int i = 0;
        double total_price = 0;
        List<CartMedicine> cartMedicineList = ( (List<CartMedicine>) session.getAttribute("cart_medicines") );
        List<Medicine> prescriptionMedicines = ( (List<Medicine>)session.getAttribute("prescription_medicines") );
    %>
    <c:forEach items="${sessionScope.get('cart_medicines')}" var="cartMedicine" varStatus="loopCounter" >
            <tr>
                <td><% out.print( prescriptionMedicines.get(i).getName() ); %></td>
                <td><% out.print( prescriptionMedicines.get(i).getCategory() ); %></td>
                <td><% out.print( prescriptionMedicines.get(i).getPrice() * ( cartMedicineList.get(i).getQuantity() ) ); %> L.E</td>
                <td><input type="number" title="medicine_quantity" id="medicine_quantity_${loopCounter.index}" value="<% out.print( cartMedicineList.get(i).getQuantity() ); %>" onchange="setMedicineQuantity('<c:out value="${cartMedicine.medicine_id}"/>', parseInt($('#medicine_quantity_${loopCounter.index}').val()))" /></td>
                <td><button class="btn btn-danger" onclick="deleteMedicine('<% out.print( prescriptionMedicines.get(i).getName() ); %>', '<c:out value="${cartMedicine.medicine_id}"/>')">Remove</button></td>
            </tr>
        <%
            total_price += prescriptionMedicines.get(i).getPrice() * ( cartMedicineList.get(i).getQuantity() );
            i++;
        %>
    </c:forEach>

    </tbody>
</table>

<div class="panel-footer" align="right"><span class="lead" style="font-weight: bold; font-size: large;color: #721c24;">Total Price : <% out.print(total_price); %> L.E </span></div>
