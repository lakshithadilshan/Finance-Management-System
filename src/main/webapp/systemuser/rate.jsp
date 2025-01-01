<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="systemuser.model.Rate" %>


<html>
<head>

    <title>Rates List</title>
</head>
<body>
<h2>Rate List</h2>
<table border="1">

 <thead>
         <tr>
             <th>ID</th>
             <th>Account Name</th>
             <th>Rate</th>
             <th>Action</th>
         </tr>
     </thead>
  <tbody>
        <%
            List<Rate> rates = (List<Rate>) request.getAttribute("rates");
            if (rates != null && !rates.isEmpty()) {
                for (Rate rate : rates) {
        %>
            <tr>
                <td ><%= rate.getRateId() %></td>
                <td><%= rate.getAccountName() %></td>
                <td><%= rate.getRate() %></td>
                <td><a href=" systemuser/updateRate.jsp?rateId=<%= rate.getRateId() %>">Update</a></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">No rates available</td>
            </tr>
        <% } %>

    </tbody>
    </table>

    </body>
</html>
