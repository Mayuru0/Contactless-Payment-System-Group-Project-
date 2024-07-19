<%-- 
    Document   : profile.jsp
    Created on : 14-Jul-2024, 20:43:33
    Author     : Thisal Karunarathna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MyProfile</title>
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
        <script src="https://kit.fontawesome.com/f7a46409df.js" crossorigin="anonymous"></script>
    </head>
    <body>

        <%
            String username = (String) session.getAttribute("user");
            String email = (String) session.getAttribute("email");
            String contact = (String) session.getAttribute("contact");
            String address = (String) session.getAttribute("address");
            String nationalID = (String) session.getAttribute("nationalID");
            String balance = (String) session.getAttribute("balance");

            if (username == null) {
                username = "Guest";
            }
            if (email == null) {
                email = "N/A";
            }
            if (contact == null) {
                contact = "+94";
            }
            if (address == null) {
                address = "N/A";
            }
            if (nationalID == null) {
                nationalID = "N/A";
            }
            if (balance == null) {
                balance = "N/A";
            }
        %>
        <div class="balance-container">
            <p style="bottom: 50px;">Available Balance</p><br>
            <i class="fa-solid fa-wallet"></i> <%= balance%>
        </div>
        <ul class="navbar">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="send.jsp">Send</a></li>
            <li><a href="receive.jsp">Receive</a></li>
            <li><a href="transactions.jsp">Transaction History</a></li>
            <li id="profile"><a class="active" href="profile.jsp"><i style="padding-right: 10px;" class="fa-solid fa-user"></i><%= username%></a></li>
                    <% if (!"Guest".equals(username)) { %>
            <li><a href="LogoutServlet"><i style="padding-right: 10px; color: red;" class="fa-solid fa-sign-out-alt"></i>Logout</a></li>
                <% } else { %>
            <li><a href="login.html"><i style="padding-right: 10px;" class="fa-solid fa-sign-in-alt"></i>Login</a></li>
                <% }%>
        </ul>

        <div class="container">
            <div class="wrapper">
                <div class="title">
                    Profile
                </div>
                <form action="UpdateProfileServlet" method="post">
                    <div class="row">
                        <i class="fas fa-user"></i>
                        <input type="text" id="username" name="username" value="<%= username%>" required placeholder="Username" readonly>
                    </div>
                    <div class="row">
                        <i class="fas fa-envelope"></i>
                        <input type="email" id="email" name="email" value="<%= email%>" required placeholder="Email">
                    </div>
                    <div class="row">
                        <i class="fas fa-phone"></i>
                        <input type="number" id="contact" name="contact" value="<%= contact%>" required placeholder="Contact NO">
                    </div>
                    <div class="row">
                        <i class="fa-solid fa-location-dot"></i>
                        <input type="text" id="address" name="address" value="<%= address%>" required placeholder="Address">
                    </div>
                    <div class="row">
                        <i class="fas fa-id-card"></i>
                        <input type="text" id="nationalID" name="nationalID" value="<%= nationalID%>" required placeholder="National ID">
                    </div>
                    <div class="row button">
                        <input type="submit" value="Update Profile">
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
