<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deposit Success</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            text-align: center;
        }
        .container {
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 8px;
            width: 50%;
            margin: 0 auto;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .container h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        .success-message {
            font-size: 18px;
            color: #28a745;
            margin-bottom: 20px;
        }
        .account-details {
            font-size: 16px;
            color: #333;
            margin-bottom: 20px;
        }
        .button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
        }
        .button:hover {
            background-color: #0056b3;
        }
        .logout-button {
                    background-color: #f44336;
                    color: white;
                    padding: 10px 20px;
                    text-align: center;
                    text-decoration: none;
                    display: inline-block;
                    font-size: 16px;
                    border: none;
                    cursor: pointer;
                    border-radius: 5px;
                    margin-top: 20px;
                }

                .logout-button:hover {
                    background-color: #e53935; /* Darker red on hover */
                }
    </style>
</head>
<body>
    <div class="container">
        <h2>Account Deposit Successfully!</h2>
        <p class="success-message">Your savings account has been deposited successfully.</p>
        <%

            String accountNumberStr = (String) request.getAttribute("newAccNum");
            String amountStr = (String) request.getAttribute("depositAmount");
                Integer accountNumber = null;

                if (accountNumberStr != null) {
                    try {
                        accountNumber = Integer.parseInt(accountNumberStr); // Convert to Integer
                        amount = Integer.parseInt(amountStr); // Convert to Integer
                    } catch (NumberFormatException e) {
                        // Handle invalid number format
                    }
                }

            if (accountNumber != null) {
        %>
        <p class="account-details">
            <strong>Account Number:</strong> <%= accountNumber %>
            <strong>Amount:</strong> <%= amount %>
        </p>
        <%
            } else {
        %>
        <p class="account-details">
            <strong>Account Number:</strong> Not available.
        </p>
        <%
            }
        %>
        <a href="/FMS/user/accountsMenu.jsp" class="button">Go to Accounts Menu</a>
        <a href="/FMS/logout" class="logout-button">Logout</a>

    </div>
</body>
</html>
