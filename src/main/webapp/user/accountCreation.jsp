<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Open Account - Finance Management System</title>

    <script src="/FMS/resources/js/accCreation.js" defer></script>
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/accountCreation.css">
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/bgimage.css">
     <!--links for make Alert-->
        <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
        <script src="/FMS/resources/js/alerts.js" defer></script>

</head>
<body>

<div class="account-container">
    <h2>Open Your Account</h2>

    <!-- Account creation form -->
    <form action="<%= request.getContextPath() %>/account" method="post">
        <label for="nic">NIC:</label>
        <input type="text" id="nic" name="nic" placeholder="987789489V" required>

        <label for="account">Choose an Account Type:</label>
        <select name="accountType" id="account" onchange="toggleTermDropdown()" required>
            <option value="saving_account" selected>Saving Account</option>
            <option value="loan_account">Loan Account</option>
            <option value="fd_account">Fixed Deposit Account</option>
        </select>

        <div id="term-container">
            <label for="term">Choose Term for Fixed Deposit:</label>
            <select name="fdTimeDuration" id="term">
                <option value="1">1 Month</option>
                <option value="3">3 Months</option>
                <option value="12">12 Months</option>
                <option value="24">24 Months</option>
            </select>
        </div>
        <div id="term-container_loan">
                    <label for="term">Choose Term for Loan :</label>
                    <select name="loanTimeDuration" id="term">
                        <option value="1">1 Year</option>
                        <option value="2">2 Year</option>
                        <option value="5">5 Year</option>
                    </select>
           </div>

        <label for="initialdeposit">Amount (LKR):</label>
        <input type="number" id="initialdeposit" name="initialDeposit" placeholder="2000" required>

        <input type="hidden" name="operation" value="accountcreation">

        <button type="submit">Open Account <i class="fa-solid fa-circle-plus"></i>
        </button>
    </form>

    <a href="http://localhost:8080/FMS/user/accountsMenu.jsp" class="logout-button"><i class="fa-solid fa-bars"></i> Menu</a>
    <!-- Logout button -->
    <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
</div>
<script>
        //  triggering success and error messages
        window.onload = function () {
            <% String successMessage = (String) request.getAttribute("successMessage"); %>
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
