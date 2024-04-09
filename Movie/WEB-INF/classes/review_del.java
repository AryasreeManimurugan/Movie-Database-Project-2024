import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class review_del extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        Statement state4 = null;
        ResultSet result = null;
        String reviewID = request.getParameter("review_id"); // Change to match the parameter name in the HTML form

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");
            System.out.println("Congratulations! You are connected successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } catch (Exception e) {
            System.err.println("Exception while loading driver");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String query = "DELETE FROM REVIEW WHERE reviewID = '" + reviewID + "'";

        out.println("<html><head><title>Review has been deleted</title>");
        out.println("</head><body>");

        try {
            state4 = con.createStatement();
            result = state4.executeQuery(query);

            out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has been deleted</H2></font>");
            out.println("</center><br />");

            out.println("<center><table border=\"1\">");
            out.println("<tr BGCOLOR=\"#cccccc\">");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\"></td>");
            out.println("</tr>");

            while (result.next()) {
                out.println("<tr>");
                out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + result.getString(1) + "</td>");
                out.println("</tr>");
            }

            out.println("</table></CENTER>");

            result.close();
            state4.close();
            con.close();
            System.out.println("Connection is closed successfully.");
        } catch (SQLException e) {
            System.err.println("SQLException while executing SQL Statement: " + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("</body></html>");
    }
}
