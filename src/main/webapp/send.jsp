<!DOCTYPE html>
<html>
    <head>
        <title>BL Contactless Payment System</title>
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"/>
        <script src="https://kit.fontawesome.com/f7a46409df.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/instascan/1.0.0/instascan.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/jsqr/dist/jsQR.js"></script>
        <style>
            #video {
                width: 400px;
                height: 300px;
                border: 1px solid black;
            }
            #canvas {
                display: none;
            }
            .disabled {
                opacity: 0.5;
                cursor: not-allowed;
            }
        </style>
    </head>
    <body>
        <%
            String username = (String) session.getAttribute("user");
            if (username == null) {
                username = "Guest";
            }
            String balance = (String) session.getAttribute("balance");
            if (balance == null) {
                balance = "00";
            }
        %>
        <div class="balance-container">
            <p style="bottom: 50px;">Available Balance</p><br>
            <i class="fa-solid fa-wallet"></i> <%= balance%>
        </div>
        <ul class="navbar">
            <li><a href="index.jsp">Home</a></li>
            <li><a class="active" href="send.jsp">Send</a></li>
            <li><a href="receive.jsp">Receive</a></li>
            <li><a href="transactions.jsp">Transaction History</a></li>
            <li class="profile"><a href="profile.jsp"><i style="padding-right: 10px;" class="fa-solid fa-user"></i><%= username%></a></li>
                    <% if (!"Guest".equals(username)) { %>
            <li><a href="LogoutServlet"><i style="padding-right: 10px; color: red;" class="fa-solid fa-sign-out-alt"></i>Logout</a></li>
                <% } else { %>
            <li><a href="login.html"><i style="padding-right: 10px;" class="fa-solid fa-sign-in-alt"></i>Login</a></li>
                <% }%>
        </ul>

        <h2>Scan QR Code</h2>
        <video id="video" autoplay></video>
        <canvas id="canvas"></canvas>
        <div>
            <form id="qr-form" action="ScanServlet" method="POST">
                <input type="hidden" id="session-user" value="<%= username%>">
                <label for="qr-result">Receiver UserName:</label>
                <div class="row input"><input type="text" id="qr-result" name="qr-result" readonly></div>
                <div class="row button">
                    <button type="submit" id="process-button" class="disabled" disabled>Send Payment</button>
                </div>
            </form>
        </div>

        <script>
            const video = document.getElementById('video');
            const canvas = document.getElementById('canvas');
            const context = canvas.getContext('2d');
            const qrResult = document.getElementById('qr-result');
            const processButton = document.getElementById('process-button');
            const form = document.getElementById('qr-form');
            const sessionUser = document.getElementById('session-user').value;

            function startVideo() {
                navigator.mediaDevices.getUserMedia({video: {facingMode: 'environment'}})
                        .then(stream => {
                            video.srcObject = stream;
                            video.setAttribute("playsinline", true);
                            video.play();
                            requestAnimationFrame(scanQRCode);
                        })
                        .catch(err => {
                            console.error("Error accessing camera: ", err);
                        });
            }

            function scanQRCode() {
                if (video.readyState === video.HAVE_ENOUGH_DATA) {
                    canvas.width = video.videoWidth;
                    canvas.height = video.videoHeight;
                    context.drawImage(video, 0, 0, canvas.width, canvas.height);
                    const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
                    const code = jsQR(imageData.data, imageData.width, imageData.height, {
                        inversionAttempts: "dontInvert",
                    });
                    if (code) {
                        qrResult.value = code.data;
                        updateButtonState();
                        sendQRCodeData(code.data);
                    }
                }
                requestAnimationFrame(scanQRCode);
            }

            function sendQRCodeData(qrCodeData) {
                fetch('/processQRCode', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({qrCodeData: qrCodeData})
                })
                        .then(response => response.json())
                        .then(data => {
                            console.log('QR Code data processed:', data);
                        })
                        .catch(error => {
                            console.error('Error processing QR code data:', error);
                        });
            }

            function updateButtonState() {
                processButton.disabled = !qrResult.value;
                processButton.classList.toggle('disabled', !qrResult.value);
            }

            qrResult.addEventListener('input', updateButtonState);

            form.addEventListener('submit', function (event) {
                const qrCodeValue = qrResult.value;
                if (qrCodeValue === sessionUser) {
                    alert("You cannot send payment to yourself!");
                    event.preventDefault(); // Prevent form submission
                }
            });

            startVideo();
        </script>
    </body>
</html>
