<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>BL Contactless Payment System</title>
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
            <li><a href="index.jsp">Home</a></li>
            <li><a class="active" href="send.jsp">Send</a></li>
            <li><a href="receive.jsp">Receive</a></li>
            <li><a href="transactions.jsp">Transaction History</a></li>
            <li class="profile"><a href="profile.jsp"><i style="padding-right: 10px;" class="fa-solid fa-user"></i><%= username %></a></li>
        </ul>
        <h1>Send</h1>
    </body>
</html>
