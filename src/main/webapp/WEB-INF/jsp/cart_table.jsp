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
<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
<script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>

<table class="table table-hover table-bordered table-striped" id="medicines_table">
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
                <td><input type="number" class="quantity" title="medicine_quantity" id="medicine_quantity_${loopCounter.index}" value="<% out.print( cartMedicineList.get(i).getQuantity() ); %>" onchange="setMedicineQuantity('<c:out value="${cartMedicine.medicine_id}"/>', parseInt($('#medicine_quantity_${loopCounter.index}').val()))" /></td>
                <td><a class="remove" href="#" data-toggle="tooltip" title="Delete from cart"><span class="fas fa-minus fa-lg" onclick="deleteMedicine('<% out.print( prescriptionMedicines.get(i).getName() ); %>', '<c:out value="${cartMedicine.medicine_id}"/>')"></span></a></td>
            </tr>
        <%
            total_price += prescriptionMedicines.get(i).getPrice() * ( cartMedicineList.get(i).getQuantity() );
            i++;
        %>
    </c:forEach>

    </tbody>
</table>

<div class="panel-footer" align="right"><span class="lead" style="font-weight: bold; font-size: large;color: #721c24;">Total Price : <% out.print(total_price); %> L.E </span></div>

<script type="text/javascript">
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>