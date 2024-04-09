import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_person_role_upd extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Connection con = null;
        PreparedStatement pstmtRole = null;

        String roleID = request.getParameter("roleID");
        String roleName = request.getParameter("roleName");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Establish database connection
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");

            // Update role name in Role table based on roleID
            String query = "UPDATE Role SET roleName = ? WHERE roleID = ?";
            pstmtRole = con.prepareStatement(query);
            pstmtRole.setString(1, roleName);
            pstmtRole.setString(2, roleID);
            int rowsAffected = pstmtRole.executeUpdate();

            if (rowsAffected > 0) {
                out.println("Role name updated successfully");
            } else {
                out.println("No role found with the provided roleID");
            }
        } catch (ClassNotFoundException | SQLException e) {
            out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (pstmtRole != null) {
                    pstmtRole.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
