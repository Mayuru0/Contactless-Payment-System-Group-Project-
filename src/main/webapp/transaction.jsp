<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Transaction Page</title>
        <link rel="stylesheet" href="style.css"> <!-- Include your stylesheet if necessary -->
    </head>
    <body>
        <%
            String receiverUser = (String) session.getAttribute("receiverUser");
            if (receiverUser == null) {
                receiverUser = "N/A";
            }
            String receiverNationalID = (String) session.getAttribute("receiverNationalID");
            if (receiverNationalID == null) {
                receiverNationalID = "N/A";
            }
            String receiverContact = (String) session.getAttribute("receiverContact");
            if (receiverContact == null) {
                receiverContact = "N/A";
            }
            String receiverEmail = (String) session.getAttribute("receiverEmail");
            if (receiverEmail == null) {
                receiverEmail = "N/A";
            }

            String balance = (String) session.getAttribute("balance");
            if (balance == null) {
                balance = "00";
            }
        %>
        <h1>Transaction Page</h1>
        <div class="balance-container">
            <p style="bottom: 50px;">Available Balance</p><br>
            <i class="fa-solid fa-wallet"></i> <%= balance%>
        </div>
        <form action="TransactionServlet" method="POST">
            <div class="row">
                <label for="receiverUser">User Name:</label>
                <input type="text" id="receiverUser" name="receiverUser" value="<%= receiverUser%>" readonly>
            </div>
            <div class="row">
                <label for="receiverEmail">Email:</label>
                <input type="text" id="receiverEmail" name="receiverEmail" value="<%= receiverEmail%>" readonly>
            </div>
            <div class="row">
                <label for="receiverContact">Contact Number:</label>
                <input type="text" id="receiverContact" name="receiverContact" value="<%= receiverContact%>" readonly>
            </div>
            <div class="row">
                <label for="receiverNationalID">National ID:</label>
                <input type="text" id="receiverNationalID" name="receiverNationalID" value="<%= receiverNationalID%>" readonly>
            </div><br><br>

            <div class="row">
                <label for="amount">Sending Amount:</label>
                <input type="number" id="amount" name="amount" step="0.01" min="0" placeholder="Enter Money Amount" required>
            </div>

            <div class="row button">
                <input type="submit" value="Proceed Transaction">
            </div>
        </form>
    </body>
</html>
