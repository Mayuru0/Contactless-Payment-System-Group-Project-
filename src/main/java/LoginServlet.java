import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost/bl-payment-system";
    private static final String DB_USER = "thisal";
    private static final String DB_PASS = "thisal123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String query = "SELECT * FROM users WHERE username=? AND password=?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            HttpSession session = request.getSession();
                            String name = request.getParameter("username");
                            session.setAttribute("user", name);
                            session.setAttribute("email", rs.getString("email"));
                            session.setAttribute("contact", rs.getString("contact"));
                            session.setAttribute("address", rs.getString("address"));
                            session.setAttribute("nationalID", rs.getString("nationalID"));
                            session.setAttribute("balance", rs.getString("balance"));
                            response.sendRedirect("index.jsp");
                        } else {
                            out.println("User doesn't exist or invalid credentials.");
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            out.println("An error occurred: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Login servlet for user authentication";
    }
}
