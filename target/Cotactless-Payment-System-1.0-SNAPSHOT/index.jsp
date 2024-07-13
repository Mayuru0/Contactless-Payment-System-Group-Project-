<!DOCTYPE html>
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
            <li><a class="active" href="index.jsp">Home</a></li>
            <li><a href="send.jsp">Send</a></li>
            <li><a href="receive.jsp">Receive</a></li>
            <li><a href="transactions.jsp">Transaction History</a></li>
            <li class="profile"><a href="profile.jsp"><i style="padding-right: 10px;" class="fa-solid fa-user"></i><%= username %></a></li>
        </ul>
        
        <div class="navbtn">
            <a href="send.jsp">
                <div class="navbtnsing">
                    <i style="font-size: 50px;" class="fa-solid fa-share-from-square"></i>
                    <p>Send Payment</p>
                </div>
            </a>
            <a href="receive.jsp">
                <div class="navbtnsing">
                    <i style="font-size: 50px;" class="fa-solid fa-inbox"></i>
                    <p>Receive Payment</p>
                </div>
            </a>
            <a href="transactions.jsp">
                <div class="navbtnsing">
                    <i style="font-size: 50px;" class="fa-solid fa-clock-rotate-left"></i>
                    <p>Transaction History</p>
                </div>
            </a>
        </div>
        <div class="image-container">
            <img src="assets/name.png" alt="Image Description">
        </div>
    </body>
</html>
