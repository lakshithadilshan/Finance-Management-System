<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employee Registration</title>
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/register.css">
    <script src="/FMS/resources/js/register.js" defer></script>
    <!--links for make Alert-->
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
    <script src="/FMS/resources/js/alerts.js" defer></script>
</head>
<body>

    <div class="registration-container">
        <div class="back-button">
            <a href="http://localhost:8080/FMS" class="btn-back"><i class="fa-solid fa-reply-all"></i> Back</a>
        </div>
        <h2>Employee Registration</h2>

        <!-- Registration Form -->
        <form action="<%= request.getContextPath() %>/sysuser" method="post" >
            <!-- Username -->
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>

            <!-- Password -->
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <!-- Confirm Password -->
            <label for="confirmpassword">Confirm Password:</label>
            <input type="password" id="confirmpassword" name="confirmPassword" required>

            <!-- Email -->
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" placeholder = "lakshitha@gmail.com" required>

            <!-- Submit Button -->
            <button type="submit">Register</button>
            <p><a href="/FMS/systemuser/login.jsp">Already have an Account</a></p>
        </form>
    </div>
<script>
        // triggering success and error messages
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
