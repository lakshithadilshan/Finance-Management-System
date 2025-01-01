<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.DecimalFormat" %>
<html>
<head>
    <title>Finance Management System</title>
 <link rel="stylesheet" type="text/css" href="/FMS/resources/css/savingAcc.css">
 <link rel="stylesheet" type="text/css" href="/FMS/resources/css/bgimage.css">
 <!--links for make Alert-->
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
    <script src="/FMS/resources/js/alerts.js" defer></script>
</head>
<body>
    <div class="container">
        <h2>Saving Account</h2>

        <div class="sections">
            <!-- Balance Inquiry Section -->
            <div class="form-section">
                <h3>Balance Inquiry</h3>
                <form action="<%= request.getContextPath() %>/savingacc" method="post">
                    <label for="accountNumber">Account Number:</label>
                    <input type="number" id="accnumber" name="accountNumber" required>
                    <input type="hidden" id="accounttype" name="accounttype" value="saving_account" required>
                    <input type="hidden" id="operation" name="operation" value="checkBalance">
                    <button class="btn" type="submit">Check Balance</button>
                </form>

                <!-- Display balance value from backend -->
                <p><strong>Your balance is: LKR </strong><%= request.getAttribute("balance") != null ? request.getAttribute("balance") : "Does not exist Account " %></p>
            </div>

            <!-- Deposit/Withdraw Money Section -->
            <div class="form-section">
                <h3>Deposit/Withdraw Money</h3>
                <form action="<%= request.getContextPath() %>/savingacc" method="post">
                    <label for="transactionAccountNumber">Account Number:</label>
                    <input type="number" id="transactionAccountNumber" name="accountNumber" required>

                    <label for="transactionType">Transaction Type:</label>
                    <select id="transactionType" name="operation" required>
                        <option value="deposit">Deposit</option>
                        <option value="withdraw">Withdraw</option>
                    </select>

                    <label for="transactionAmount">Amount: LKR</label>
                    <input type="number" id="transactionAmount" name="amount" required step="0.01" min="1">

                    <button class="btn" type="submit">Enter</button>
                </form>

                <p><strong></strong><%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "Have a Nice Day!" %></p>

            </div>
        </div>

        <!-- Menu and Logout Buttons -->
        <div>
            <a href="http://localhost:8080/FMS/user/accountsMenu.jsp" class="logout-button"><i class="fa-solid fa-bars"></i>Menu</a>
            <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
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
