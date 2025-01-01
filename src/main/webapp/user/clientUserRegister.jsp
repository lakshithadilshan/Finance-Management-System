<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Form</title>
  <link rel="stylesheet" type="text/css" href="/FMS/resources/css/clientUserRegister.css">
  <link rel="stylesheet" type="text/css" href="/FMS/resources/css/bgimage.css">
 <!--links for make Alert-->
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
    <script src="/FMS/resources/js/alerts.js" defer></script>
</head>
<body>

    <div class="registration-container">
        <h2>Registration Form</h2>



        <!-- Registration Form -->
        <form action="<%= request.getContextPath() %>/client" method="post">

            <!-- Full Name -->
            <label for="cNameinFull">Full Name:</label>
            <input type="text" id="cNameinFull" name="cNameFull" placeholder="Hettiarachchige Lakshitha Isuru Dilshan" required>

            <!-- Name with Initials -->
            <label for="NamewithInitials">Name with Initials:</label>
            <input type="text" id="NamewithInitials" name="nameWithInitials" placeholder="A.B. Smith" required>

            <!-- City -->
            <label for="city">City:</label>
            <input type="text" id="city" name="city" placeholder = "Hakmana"required>

            <!-- Email -->
            <label for="cemail">Email Address:</label>
            <input type="email" id="cemail" name="cEmail" placeholder="lakshitha@gmail.com" required>

            <!-- CNIC -->
            <label for="cnic">NIC:</label>
            <input type="text" id="cnic" name="cNic" placeholder="982780489V" required>

            <!-- Marital Status -->
            <label for="cmartial_status">Marital Status:</label>
            <select name="cMartialStatus" id="cmartial_status" required>
                <option value="Single">Single</option>
                <option value="Married">Married</option>
                <option value="Divorced">Divorced</option>
                <option value="Widowed">Widowed</option>
            </select>

            <!-- Password -->
            <label for="cpasswordsign">Password:</label>
            <input type="password" id="cpasswordsign" name="cPasswordDesign" placeholder="123456Wd@" required>

            <!-- Address -->
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" placeholder="Kumari,Kongala,Hakmana" required>

            <!-- Submit Button -->
            <button type="submit"> <i class="fa-solid fa-square-plus"></i>  Register</button>
            <a href="http://localhost:8080/FMS/user/accountsMenu.jsp" class="logout-button"><i class="fa-solid fa-bars"></i> Menu</a>
            <!-- Logout button -->
            <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
        </form>

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
