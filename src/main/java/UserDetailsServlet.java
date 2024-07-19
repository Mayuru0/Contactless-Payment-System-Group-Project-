import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URL = "jdbc:mysql://localhost/bl-payment-system";
    private static final String DB_USER = "thisal";
    private static final String DB_PASS = "thisal123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Establish the database connection
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Prepare and execute the SQL query
            String sql = "SELECT nationalID, email, contact FROM users WHERE username = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nationalID = rs.getString("nationalID");
                String email = rs.getString("email");
                String contact = rs.getString("contact");

                // Construct the JSON response
                String jsonResponse = String.format("{\"nationalID\":\"%s\",\"email\":\"%s\",\"contact\":\"%s\"}",
                        nationalID, email, contact);
                out.write(jsonResponse);
            } else {
                out.write("{}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\":\"An error occurred.\"}");
        } finally {
            // Close resources in the reverse order they were opened
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}
