/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thisal Karunarathna
 */
@WebServlet(urlPatterns = {"/ScanServlet"})
public class ScanServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost/bl-payment-system";
    private static final String DB_USER = "thisal";
    private static final String DB_PASS = "thisal123";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ScanServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ScanServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("text/html");

    String username = request.getParameter("qr-result");
    HttpSession session = request.getSession();

    
    PrintWriter out = response.getWriter();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String query = "SELECT * FROM users WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Store receiver information in session
                        session.setAttribute("receiverUser", username);
                        session.setAttribute("receiverEmail", rs.getString("email"));
                        session.setAttribute("receiverContact", rs.getString("contact"));
                        session.setAttribute("receiverNationalID", rs.getString("nationalID"));
                        response.sendRedirect("transaction.jsp");
                    } else {
                        out.println("<html><body>");
                        out.println("<h3>User doesn't exist or invalid credentials.</h3>");
                        out.println("<a href='index.jsp'>Go back</a>");
                        out.println("</body></html>");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ScanServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("<html><body>");
                out.println("<h3>An SQL error occurred: " + ex.getMessage() + "</h3>");
                out.println("<a href='index.jsp'>Go back</a>");
                out.println("</body></html>");
            }
        }
    } catch (IOException | ClassNotFoundException | SQLException e) {
        out.println("<html><body>");
        out.println("<h3>An error occurred: " + e.getMessage() + "</h3>");
        out.println("<a href='index.jsp'>Go back</a>"); 
        out.println("</body></html>");
    } finally {
        out.close();
    }
}



    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
