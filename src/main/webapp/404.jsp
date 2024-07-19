<%-- 
    Document   : 404.jsp
    Created on : 19-Jul-2024, 15:44:00
    Author     : Thisal Karunarathna
--%>

<html>
    <head>
        <title>BL Contactless Payment System | 404</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
        <script src="https://kit.fontawesome.com/f7a46409df.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <%
            String username = (String) session.getAttribute("user");
            if (username == null) {
                username = "Guest";
            }
        %>
        <ul class="navbar">
            <li><a class="active" href="index.jsp">Home</a></li>
            <li><a href="send.jsp">Send</a></li>
            <li><a href="receive.jsp">Receive</a></li>
            <li><a href="transactions.jsp">Transaction History</a></li>
            <li class="profile"><a href="profile.jsp"><i style="padding-right: 10px;" class="fa-solid fa-user"></i><%= username%></a></li>
                    <% if (!"Guest".equals(username)) { %>
            <li><a href="LogoutServlet"><i style="padding-right: 10px; color: red;" class="fa-solid fa-sign-out-alt"></i>Logout</a></li>
                <% }%>
        </ul>

        <h1>Something went Wrong Code:404</h1>
    </body>
</html>
