<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="systemuser.model.TransactionType" %>
<%@ page import="systemuser.model.Rate" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Management</title>
    <link rel="stylesheet" type="text/css" href="/FMS/resources/css/setting.css">
    <!--links for make Alert-->
        <link rel="stylesheet" type="text/css" href="/FMS/resources/css/alerts.css">
        <script src="/FMS/resources/js/alerts.js" defer></script>

</head>
<body>
    <div class="container">
        <h2>Admin</h2>

        <!-- Transaction Types Section -->
        <div class="section">
            <h3>Transaction Types</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Transaction Type</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
<%
    List<TransactionType> transactionTypes = (List<TransactionType>) request.getAttribute("transactionTypes");
    if (transactionTypes != null) {
        for (TransactionType transactionType : transactionTypes) {
            boolean isEnabled = transactionType.getStatus() == 0; // 0 for Enabled, 1 for Disabled
%>
                    <tr>
                        <td><%= transactionType.getTxnId() %></td>
                        <td><%= transactionType.getTxnType() %></td>
                        <td><%= isEnabled ? "Enabled" : "Disabled" %></td>
                        <td>
                            <form action= "./admin" method ="post">
                                <input type="hidden" name ="operation" value="txnTypeStatusUpdate">
                                <input type="hidden" name ="txnId" value="<%= transactionType.getTxnId() %>">
                                <input type="hidden" name ="updateStatus" value="<%= isEnabled ? "1" : "0" %>">
                                <button type="submit" class="btn" style="background-color: <%= isEnabled ? "#f44336" : "#4CAF50" %>" >
                                    <%= isEnabled ? "Disable" : "Enable" %>
                                </button>
                            </form>
                        </td>
                    </tr>
<%
        }
    }
%>
                </tbody>
            </table>
        </div>
       <!-- <hr>
            <center><%= session.getAttribute("updateRMsg") != null ? session.getAttribute("updateRMsg") : "" %></center>
            <hr>-->
        <!-- Rates Section -->
        <div class="section">
            <h3>Rates</h3>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Account Name</th>
                        <th>Rate (%)</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
<%
    List<Rate> rates = (List<Rate>) request.getAttribute("rates");
    if (rates != null) {
        for (Rate rate : rates) {
%>
                    <tr>
                        <td><%= rate.getRateId() %></td>
                        <td><%= rate.getAccountName() %></td>
                        <td><%= rate.getRate() %></td>
                        <td>
                            <!-- Update button that triggers the modal -->
                            <button class="btn btn-update" onclick="openModal(<%= rate.getRateId() %>, '<%= rate.getAccountName() %>', <%= rate.getRate() %>)">
                                Update
                            </button>
                        </td>
                    </tr>
<%
        }
    }
%>
                </tbody>
            </table>
        </div>

        <!-- Modal for Rate Update -->
        <div id="updateRateModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h4>Update Rate</h4>
                <form id="updateRateForm" action="./rates" method="post">
                    <input type="hidden" name="rateId" id="rateId" />
                    <label for="accountName">Account Name:</label>
                    <input type="text" id="accountName" name="accountName" readonly><br><br>
                    <label for="rate">Rate (%):</label>
                    <input type="number" id="rate" name="rate" step="0.01" required><br><br>
                    <input type="submit" value="Update Rate" class="btn btn-update">
                    <button type="button" class="btn btn-cancel" onclick="closeModal()">Cancel</button>
                </form>
            </div>
        </div>

        <p><a href="/FMS/logout" class="btn" style="background-color: #f44336;">Back</a>
        </p>

    </div>

    <script>
        // Function to open the modal
        function openModal(rateId, accountName, currentRate) {
            document.getElementById("rateId").value = rateId;
            document.getElementById("accountName").value = accountName;
            document.getElementById("rate").value = currentRate;
            document.getElementById("updateRateModal").style.display = "block";
        }

        // Function to close the modal
        function closeModal() {
            document.getElementById("updateRateModal").style.display = "none";
        }

        // Close the modal if clicked outside of it
        window.onclick = function(event) {
            if (event.target == document.getElementById("updateRateModal")) {
                closeModal();
            }
        }
    </script>
    <!--alerts-->
    <script>
        // Triggering success and error messages
        window.onload = function () {
            // Retrieve messages from request attributes
            let successMessage = "<%= request.getAttribute("updateRMsg") != null ? request.getAttribute("updateRMsg") : "" %>";
            let errorMessage = "<%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>";

            // Retrieve messages from session attributes
            let sessionSuccessMessage = "<%= session.getAttribute("updateRMsg") != null ? session.getAttribute("updateRMsg") : "" %>";
            let sessionErrorMessage = "<%= session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage") : "" %>";

            // Display success messages
            if (successMessage) {
                showAlert(successMessage, "success");
            } else if (sessionSuccessMessage) {
                showAlert(sessionSuccessMessage, "success");

                // Clear session attribute to avoid duplicate messages
                <% session.removeAttribute("updateRMsg"); %>
            }

            // Display error messages
            if (errorMessage) {
                showAlert(errorMessage, "error");
            } else if (sessionErrorMessage) {
                showAlert(sessionErrorMessage, "error");

                // Clear session attribute to avoid duplicate messages
                <% session.removeAttribute("errorMessage"); %>
            }
        };
    </script>



</body>
</html>
