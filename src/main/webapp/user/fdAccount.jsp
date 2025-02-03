<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FD Account - Finance Management System</title>
 <link rel="stylesheet" type="text/css" href="/FMS/resources/css/fdAccount.css">
 <link rel="stylesheet" type="text/css" href="/FMS/resources/css/bgimage.css">
 <!--links for make Alert-->
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
    <script src="/FMS/resources/js/alerts.js" defer></script>
</head>
<body>

<div class="account-container">
    <!-- Account Details Form -->
    <div class="form-block">
        <h3>Search FD Account Details</h3>
        <form action="<%= request.getContextPath() %>/fdAccount" method="post">
            <label for="accnnum">Account Number:</label>
            <input type="text" id="accnnum" name="accNum" placeholder="Enter account number ex:67761873" required>

            <p><strong>Maturity Date: </strong><%= request.getAttribute("mDate") != null ? request.getAttribute("mDate") : "No maturity date available" %></p>
            <p><strong>Interest Earned: LKR </strong><%= request.getAttribute("interestEarned") != null ? request.getAttribute("interestEarned") : "No interest data available" %></p>

            <input type="hidden" name="operation" value="fdDetails">
            <br><br>
            <button type="submit">Search</button>
        </form>
    </div>

    <!-- Withdrawal Form -->
    <div class="form-block">
        <h3>Withdraw Funds</h3>
        <form action="<%= request.getContextPath() %>/fdAccount" method="post">
            <label for="withdrawAccNum">Account Number:</label>
            <input type="number" id="withdrawAccNum" name="accNum" placeholder="Enter account number ex:67761873" required>

            <p><strong> </strong><%= request.getAttribute("successMsgFD") != null ? request.getAttribute("successMsgFD") : "Welcome" %></p>
            <input type="hidden" id="withdrawAmount" name="withdrawAmount" placeholder="Enter amount" required>

            <input type="hidden" name="operation" value="fdWithdraw">
            <button type="submit">Withdraw</button>
        </form>
        <div class="menu-links">
        <br>
                <a href="http://localhost:8080/FMS/user/accountsMenu.jsp" class="logout-button"><i class="fa-solid fa-bars"></i> Menu</a>
                <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
            </div>
    </div>

</div>
<!-- alert script -->
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