<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Finance Management System</title>
  <link rel="stylesheet" type="text/css" href="/FMS/resources/css/loginc.css">

</head>
<body>

    <div class="login-container">
        <div class="back-button">
            <a href="http://localhost:8080/FMS" class="btn-back"><i class="fa-solid fa-reply-all"></i> Back</a>
        </div>
        <h2>Login to Client Service Account</h2>

        <!-- Form for login -->
        <form action="<%= request.getContextPath() %>/login" method="post">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <input type="hidden" id="username" name="side" value="clientSupportPanel" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <button type="submit"><i class="fa-solid fa-hands-asl-interpreting"></i> Login</button>
        </form>

        <!-- Show error message if login fails -->
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="error"><%= errorMessage %></div>
        <%
            }
        %>
    </div>

    <footer>
        <p>&copy; 2024 Finance Management System | All Rights Reserved</p>
    </footer>

</body>
</html>
