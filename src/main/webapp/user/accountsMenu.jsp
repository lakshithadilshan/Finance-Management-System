<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Menu - Finance Management System</title>
<link rel="stylesheet" type="text/css" href="/FMS/resources/css/style.css">

</head>
<body>

    <div class="menu-container">
        <h2><i class="fa-solid fa-bars"></i> </i> Menu</h2>


         <form action="/FMS/user/clientUserRegister.jsp" method="post">
              <button type="submit"><i class='fas fa-user-plus'></i>    Client Register</button>
         </form>
        <!-- Account Creation Button -->
        <form action="/FMS/user/accountCreation.jsp" method="post">
            <button type="submit"><i class='fas fa-plus-circle'></i>    Open Account</button>
        </form>

        <form action="/FMS/user/loanAccount.jsp" method="post">
            <button type="submit"><i class='fas fa-hand-holding-usd'></i>   Loan Account</button>
        </form>

        <form action="/FMS/user/fdAccount.jsp" method="post">
            <button type="submit"><i class='fas fa-piggy-bank'></i> Fixed Deposit Account</button>
        </form>

        <form action="/FMS/user/savingAccount.jsp" method="post">
            <button type="submit"><i class='fas fa-wallet'></i>     Saving Account</button>
        </form>

        <form action="" method="post">
           <button type="submit"><i class=''></i> Passbook</button>
        </form>

        <!-- Logout Button -->
        <a href="/FMS/logout" class="logout-button"><i class='fas fa-sign-out-alt'></i>     Logout</a>
    </div>

</body>
</html>
