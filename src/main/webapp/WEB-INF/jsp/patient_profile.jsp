<%@ page import="com.graduationproject.model.Patient" %>
<%@ page import="com.graduationproject.model.Medicine" %>
<%@ page import="java.util.List" %>
<%@ page import="com.graduationproject.model.CartMedicine" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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



    <title><% out.print(((Patient)session.getAttribute("patient")).getUsername()); %>'s Profile</title>

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
                        <a class="nav-link js-scroll-trigger" href="/access_patient">Access Patient History</a>
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
        <div class="container h-100">
            <div class="row h-100">
                <div class="col-lg-12 my-auto">
                    <div class="header-content mx-auto">
                        <div class="panel panel-default">
                            <!-- Default panel contents -->
                            <div class="panel-heading" align="center"><span class="lead" style="font-weight: bold; font-size: large">Current Medical History </span></div>
                            <br>
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
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="masthead" id="new_prescription">
        <div class="col-lg-12 my-auto">
            <div class="header-content mx-auto">
                <div class="col-md-12 text-center">
                    <br>
                    <h1>Welcome</h1>
                    <br>
                    <c:if test="${not empty cart_error}">
                        <div class="alert alert-danger">
                            <p align="center">${cart_error}</p>
                        </div>
                    </c:if>
                    <input class="form-control" id="search_medicine" type="text" placeholder="Medicine name" style="font-weight: bold; font-size: large"/>
                </div>
                <br><br>
                <div class="panel panel-default">
                    <!-- Default panel contents -->
                    <div class="panel-heading" align="center"><span class="lead" style="font-weight: bold; font-size: large">Current Prescription </span></div>
                    <br>
                    <table class="table table-hover" id="medicines">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Price</th>
                        </tr>
                        </thead>
                        <tbody id="prescription_medicines">
                            <tr>
                                <td>---</td>
                                <td>---</td>
                                <td>---</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <input type="submit" class="btn btn-block btn-primary btn-info" id="done_search" value="Next" />
            </div>
        </div>
    </section>

    <footer>
        <div class="container">
            <p>&copy; Graduation 2018. All Rights Reserved</p>
        </div>
    </footer>


    <!-- Bootstrap core JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery/js/jquery-3.3.1.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.min.js" />"></script>

    <!-- Plugin JavaScript -->
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>

    <!-- Custom scripts for this template -->
    <script src="<c:url value="/resources/js/new-age.min.js" />"></script>

    <script defer src="<c:url value="/resources/vendor/font-awesome/js/fontawesome-all.min.js"/>"></script>

    <script defer src="<c:url value="/resources/vendor/jquery/js/jquery-ui.js"/>"></script>


    <script type="text/javascript">

        var max_suggestions = 20;
        $(function() {

            var medicines_list = $.map( ${medicines_list}, function (value, key) {return {label: value.name, value: value.name, id: value.id, name: value.name, category: value.category, form: value.form, active_ingredients: value.active_ingredients.replace("\r\n",""), price: value.price, quantity: value.quantity}});
            var cart_medicines = [];
            var prescription_medicines = [];

            <c:if test="${not empty sessionScope.get('prescription_medicines') && not empty sessionScope.get('cart_medicines')}">
                <% int i = 0; %>
                <c:forEach items='<%= ((List<Medicine>)session.getAttribute("prescription_medicines")) %>' var="x"  >
                    prescription_medicines.push({
                        label: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getName()) %>',
                        value: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getName()) %>',
                        id: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getId()) %>',
                        name: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getName()) %>',
                        category: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getCategory()) %>',
                        form: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getForm()) %>',
                        active_ingredients: '<%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getActive_ingredients().replace("\r\n","")) %>',
                        price: <%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getPrice()) %>,
                        quantity: <%=(((List<Medicine>)session.getAttribute("prescription_medicines")).get(i).getQuantity()) %>
                    });
                    cart_medicines.push({
                        medicine_id: '<%=(((List<CartMedicine>)session.getAttribute("cart_medicines")).get(i).getMedicine_id()) %>',
                        quantity: <%=(((List<CartMedicine>)session.getAttribute("cart_medicines")).get(i).getQuantity()) %>,
                        repeat_duration: <%=(((List<CartMedicine>)session.getAttribute("cart_medicines")).get(i).getRepeat_duration()) %>,
                        prescription_id: '<%=(((List<CartMedicine>)session.getAttribute("cart_medicines")).get(i).getPrescription_id()) %>'
                    });
                    <% i++; %>
                </c:forEach>
            </c:if>

            reloadPrescriptionTable();

            $("#search_medicine").autocomplete({
                source: function(request, response) {
                    var suggestions = $.map(medicines_list, function(item) {
                        if( item.value.indexOf(request.term.toLowerCase()) === 0 ) {return item;}
                    });
                    response(suggestions.slice(0, max_suggestions));
                },
                select: function (event, ui) {
                    if (ui.item.quantity > 0) {
                        medicines_list.splice($.inArray(ui.item, medicines_list),1);
                        cart_medicines.push({
                            "medicine_id": ui.item.id,
                            "quantity": 1,
                            "repeat_duration": 0
                        });
                        prescription_medicines.push(ui.item);
                        reloadPrescriptionTable();
                        $(this).val("");
                    } else {
                        alert("Sorry this medicine is out of stock/nplease try again later");
                    }
                    return false;
                }
            });
            function reloadPrescriptionTable() {
                if (prescription_medicines.length > 0) {
                    var innerHTML = "";
                    for (var index = 0 ; index < prescription_medicines.length ; index++) {
                        innerHTML += "<tr>" +
                            "<td>" + prescription_medicines[index].name + "</td>" +
                            "<td>" + prescription_medicines[index].category + "</td>" +
                            "<td>" + prescription_medicines[index].price + " L.E</td>" +
                            "</tr>";
                    }
                    $("#prescription_medicines").html(innerHTML);
                }
            }

            function submit(action, method, values) {
                var form = $('<form/>', {
                    action: action,
                    method: method,
                    enctype: "text/plain",
                    "accept-charset": "UTF-8"
                });
                $.each(values, function() {
                    form.append($('<input/>', {
                        type: 'hidden',
                        name: this.name,
                        value: this.value
                    }));
                });
                form.appendTo('body').submit();
            }
            $("#done_search").click(function(){
                if (prescription_medicines.length === 0) {
                    alert("Add at least one medicine to commit the prescription");
                } else {
                    submit("/start_cart", "POST", [{name: "jsonParser", value: JSON.stringify({ "cart_medicines": cart_medicines, "prescription_medicines": prescription_medicines})}]);
                }
            });
        });

    </script>

</body>

</html>
