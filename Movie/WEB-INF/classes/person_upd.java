import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class person_upd extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        String personID = request.getParameter("personID");
        String personName = request.getParameter("personName");
        String personGender = request.getParameter("personGender");
        String personDateOfBirth = request.getParameter("personDateOfBirth");
        String personSalary = request.getParameter("personSalary");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");

            String query = "UPDATE MoviePerson SET personName=?, personGender=?, personDateOfBirth=?, personSalary=? WHERE personID=?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, personName);
            preparedStatement.setString(2, personGender);
            preparedStatement.setString(3, personDateOfBirth);
            preparedStatement.setString(4, personSalary);
            preparedStatement.setString(5, personID);
            
            int rowsUpdated = preparedStatement.executeUpdate(); // Assign the result to a variable

            out.println("<html><head><title>Person has been updated</title>");
            out.println("</head><body>");
            out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has updated</H2></font>");
            out.println("</center><br />");

            out.println("<center><table border=\"1\">");
            out.println("<tr BGCOLOR=\"#cccccc\">");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\"> </td>");
            out.println("</tr>");

            // If you need to retrieve any data after update, you can execute another query here

            out.println("</table></CENTER>");

            preparedStatement.close();
            con.close();
            System.out.println("Connection is closed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (result != null) result.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        out.println("</body></html>");
    }
}
