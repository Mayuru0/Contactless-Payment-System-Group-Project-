import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/generateQRCode")
public class QRCodeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String qrText = request.getParameter("qrText");
        if (qrText == null || qrText.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "QR text is required");
            return;
        }

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200);

            response.setContentType("image/png");
            try (OutputStream out = response.getOutputStream()) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            }
        } catch (Exception e) {
            throw new ServletException("QR Code generation error", e);
        }
    }
}
