import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_person_role_del extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Connection con = null;
        Statement state4 = null;
        ResultSet result = null;
        
        String movieID = request.getParameter("movieID");
        String personID = request.getParameter("personID");
        String roleID = request.getParameter("roleID");
        String roleName = request.getParameter("roleName");

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");
            System.out.println("Congratulations! You are connected successfully.");
            state4 = con.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } catch (Exception e) {
            System.err.println("Exception while loading driver");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String query = "DELETE FROM MOVIE_PERSON_ROLE WHERE movieID = '" + movieID + "' OR personID = '" + personID + "' OR roleID = '" + roleID + "' OR roleName = '" + roleName + "'";
        
        out.println("<html><head><title>Movie Role has been deleted</title></head><body>");
        out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has been deleted</H2></font>");
        out.println("</center><br />");

        try {
            int rowsAffected = state4.executeUpdate(query);
            out.println("<center>Number of rows affected: " + rowsAffected + "</center>");
        } catch (SQLException e) {
            System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
            out.println("An error occurred while executing the SQL statement.");
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (state4 != null) {
                    state4.close();
                }
                if (con != null) {
                    con.close();
                }
                System.out.println("Connection is closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("</body></html>");
    }
}
