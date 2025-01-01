<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Account - Finance Management System</title>
<link rel="stylesheet" type="text/css" href="/FMS/resources/css/accountCreation.css">
<link rel="stylesheet" type="text/css" href="/FMS/resources/css/bgimage.css">
 <!--links for make Alert-->
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
    <script src="/FMS/resources/js/alerts.js" defer></script>
</head>
<body>

<div class="account-container">
    <h2>Loan Account</h2>


    <form action="<%= request.getContextPath() %>/loan" method="post">
        <h3>Check Remaining Balance</h3>
        <label for="accnum">Loan Account Number:</label>
        <input type="number" id="accnumber" name="accnumber" placeholder="Enter loan account number ex:3001" required>
        <input type="hidden" name="operation" value="checkbalance">

        <button type="submit" >Check Balance <i class="fa-solid fa-magnifying-glass-dollar"></i></button>
    </form>

         <p><strong>Remaining Balance: LKR </strong><%= request.getAttribute("remainBalance") != null ? request.getAttribute("remainBalance") : "No Remain data available" %></p>


    <hr>

    <!-- Block 2: Repay Loan -->
    <form action="<%= request.getContextPath() %>/loan" method="post">
        <h3>Repay Loan</h3>
        <label for="accnum">Loan Account Number:</label>
        <input type="number" id="accnumber" name="accnumber" placeholder="Enter loan account number ex:3001" required>
        <label for="repaymentAmount">Repayment Amount (LKR):</label>
        <input type="number" id="repaymentAmount" name="repaymentAmount" placeholder="Enter amount to repay" min="0" step="0.01" required>
        <input type="hidden" name="operation" value="repay">

        <button type="submit" name="action" value="repayLoan">Repay Loan <i class="fa-solid fa-money-check-dollar"></i></button>
    </form>
             <p><strong></strong><%= request.getAttribute("successMsg") != null ? request.getAttribute("successMsg") : "" %></p>
             <p><strong></strong><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %></p>



    <hr>

    <!-- Back to menu and logout buttons -->
    <a href="http://localhost:8080/FMS/user/accountsMenu.jsp" class="logout-button"><i class="fa-solid fa-bars"></i> Menu</a>
    <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
</div>
<script>
        // Example of triggering success and error messages
        window.onload = function () {
            <% String successMessage = (String) request.getAttribute("successMsg"); %>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>

            <% if (successMessage != null) { %>
                showAlert("<%= successMessage %>", "success");
            <% } %>
            <% if (errorMessage != null) { %>
                showAlert("<%= errorMessage %>", "error");
            <% } %>
        };
    </script>
</body>
</html>
