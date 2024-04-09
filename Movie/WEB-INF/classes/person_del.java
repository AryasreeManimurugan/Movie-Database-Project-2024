import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class person_del extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        Statement state4 = null;
        ResultSet result = null;

        String personID = request.getParameter("personID");
        String personName = request.getParameter("personName");

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");
            System.out.println("Congratulations! You are connected successfully.");
            state4 = con.createStatement();

            String query = "DELETE FROM MoviePerson WHERE personID = '" + personID + "' OR personName = '" + personName + "'";
            state4.executeUpdate(query);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<html><head><title>Person has been deleted</title></head><body>");
            out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has been deleted</H2></font>");
            out.println("</center><br />");

            out.println("<center><table border=\"1\">");
            out.println("<tr BGCOLOR=\"#cccccc\">");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\"></td>");
            out.println("</tr>");

            out.println("<tr>");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + personID + "</td>");
            out.println("</tr>");

            out.println("</table></CENTER>");
            out.println("</body></html>");

        } catch (SQLException e) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
