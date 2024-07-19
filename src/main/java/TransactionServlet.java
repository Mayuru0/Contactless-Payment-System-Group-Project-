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
@WebServlet(urlPatterns = {"/TransactionServlet"})
public class TransactionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TransactionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TransactionServlet at " + request.getContextPath() + "</h1>");
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
        // Retrieve form parameters
        String amountStr = request.getParameter("amount");

        // Convert amount to numeric type
        double amount = 0.0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            // Handle invalid number format
            response.sendRedirect("404.jsp?message=Invalid amount");
            return;
        }

        // Get current username and receiver username from session
        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("user");
        String receiverUsername = (String) session.getAttribute("receiverUser");

        // Database connection parameters
        String jdbcURL = "jdbc:mysql://localhost/bl-payment-system";
        String dbUser = "thisal";
        String dbPassword = "thisal123";

        // Update profiles in the database
        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            // Disable auto-commit to handle transactions
            connection.setAutoCommit(false);

            // Check sender's current balance
            double currentBalance = 0.0;
            String sqlGetBalance = "SELECT balance FROM users WHERE username = ?";
            try (PreparedStatement stmtGetBalance = connection.prepareStatement(sqlGetBalance)) {
                stmtGetBalance.setString(1, currentUsername);
                ResultSet rs = stmtGetBalance.executeQuery();
                if (rs.next()) {
                    currentBalance = rs.getDouble("balance");
                } else {
                    // Handle case where balance retrieval failed
                    connection.rollback();
                    response.sendRedirect("404.jsp");
                    return;
                }
            }

            // Check if the sender has sufficient balance
            if (currentBalance < amount) {
                connection.rollback();
                response.sendRedirect("404.jsp?message=Insufficient balance");
                return;
            }

            // Prepare SQL statements
            String sqlUpdateSender = "UPDATE users SET balance = balance - ? WHERE username = ?";
            String sqlUpdateReceiver = "UPDATE users SET balance = balance + ? WHERE username = ?";

            // Update sender's balance
            try (PreparedStatement stmtUpdateSender = connection.prepareStatement(sqlUpdateSender)) {
                stmtUpdateSender.setDouble(1, amount);
                stmtUpdateSender.setString(2, currentUsername);

                int rowsUpdatedSender = stmtUpdateSender.executeUpdate();
                if (rowsUpdatedSender == 0) {
                    // Sender not found or update failed
                    connection.rollback();
                    response.sendRedirect("404.jsp?message=Update failed for sender");
                    return;
                }
            }

            // Update receiver's balance
            try (PreparedStatement stmtUpdateReceiver = connection.prepareStatement(sqlUpdateReceiver)) {
                stmtUpdateReceiver.setDouble(1, amount);
                stmtUpdateReceiver.setString(2, receiverUsername);

                int rowsUpdatedReceiver = stmtUpdateReceiver.executeUpdate();
                if (rowsUpdatedReceiver == 0) {
                    // Receiver not found or update failed
                    connection.rollback();
                    response.sendRedirect("404.jsp?message=Update failed for receiver");
                    return;
                }
            }

            // Commit transaction
            connection.commit();

            // Retrieve new balance for current user
            double newBalance;
            try (PreparedStatement stmtGetBalance = connection.prepareStatement(sqlGetBalance)) {
                stmtGetBalance.setString(1, currentUsername);
                ResultSet rs = stmtGetBalance.executeQuery();
                if (rs.next()) {
                    newBalance = rs.getDouble("balance");
                } else {
                    // Handle case where balance retrieval failed
                    response.sendRedirect("404.jsp?message=Failed to retrieve new balance");
                    return;
                }
            }

            // Update session with new balance
            session.setAttribute("balance", String.format("%.2f", newBalance));

            // Redirect to profile page
            response.sendRedirect("index.jsp");

        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
            // Handle database errors and rollback

            response.sendRedirect("404.jsp?message=Database error occurred");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
