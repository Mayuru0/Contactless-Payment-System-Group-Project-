import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONObject;

@WebServlet("/processQRCode")
public class QRCodeProcessServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        String qrCodeData = jsonObject.getString("qrCodeData");

        // Process the QR code data (e.g., validate, store in database, etc.)

        response.setContentType("application/json");
        response.getWriter().write("{\"status\":\"success\",\"qrCodeData\":\"" + qrCodeData + "\"}");
        
    }
}
