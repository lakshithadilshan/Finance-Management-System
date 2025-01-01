<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="systemuser.model.Rate" %>
<%@ page import="systemuser.Dao.rateDao" %>

<%
    int rateId = Integer.parseInt(request.getParameter("rateId"));
    rateDao dao = new rateDao();
    Rate rate = dao.getRateById(rateId); // Fetch the rate by ID
%>

<html>
<head>
    <title>Update Rate</title>
</head>
<body>
    <h2>Update Rate</h2>
    <form action="../rates" method="post">
        <input type="hidden" name="rateId" value="<%= rate.getRateId() %>">

        <label for="accountName">Account Name:</label>
        <input type="text" id="accountName" name="accountName" value="<%= rate.getAccountName() %>" readonly><br><br>

        <label for="rate">Rate:</label>
        <input type="text" id="rate" name="rate" value="<%= rate.getRate() %>"><br><br>

        <button type="submit">Update</button>
    </form>
</body>
</html>