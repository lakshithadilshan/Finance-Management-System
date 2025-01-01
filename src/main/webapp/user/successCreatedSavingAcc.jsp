<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Creation Success</title>
  <link rel="stylesheet" type="text/css" href="/FMS/resources/css/successCreated.css">

</head>
<body>
    <div class="container">
    <%
                String accountNumberStr = (String) request.getAttribute("newAccNum");
                    Integer accountNumber = null;

                    if (accountNumberStr != null) {
                        try {
                            accountNumber = Integer.parseInt(accountNumberStr); // Convert to Integer
                        } catch (NumberFormatException e) {
                            // Handle invalid number format
                        }
                    }

                if (accountNumber != null) {
            %>
        <h2>Account Created Successfully!</h2>
        <p class="success-message">Your account has been created successfully.</p>

        <p class="account-details">
            <strong>Account Number:</strong> <%= accountNumber %>
        </p>
        <%
            } else {
        %>
        <h2 style= "color:ff0000">Account Creation Failed!</h2>
        <p class="error-message">Warning :Your account creation Unsuccessfully!</p>
        <p class="account-details">
            <strong>This Client already created Account.</strong>
        </p>
        <%
            }
        %>
        <a href="/FMS/user/accountsMenu.jsp" class="button"> <i class="fa-solid fa-bars"></i>Go to Accounts Menu</a>
        <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>

    </div>
</body>
</html>
