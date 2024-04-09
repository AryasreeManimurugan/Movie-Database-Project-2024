import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class reviewJdbc extends HttpServlet {

    private static final String DB_URL = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    private static final String DB_USERNAME = "CSI3450";
    private static final String DB_PASSWORD = "AryaMani1";

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String movieID = request.getParameter("movieID");
        String starRating = request.getParameter("starRating");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Congratulations! You are connected successfully.");
            
            String query = "SELECT reviewID, reviewDescription, starRating, movieID FROM REVIEW WHERE 1=1";

            if (movieID != null && !movieID.isEmpty()) {
                query += " AND movieID = ?";
            }

            if (starRating != null && !starRating.isEmpty()) {
                query += " AND starRating = ?";
            }

            preparedStatement = con.prepareStatement(query);
            int paramIndex = 1; // Index for prepared statement parameters
            if (movieID != null && !movieID.isEmpty()) {
                preparedStatement.setString(paramIndex++, movieID);
            }
            if (starRating != null && !starRating.isEmpty()) {
                int rating = Integer.parseInt(starRating);
                preparedStatement.setInt(paramIndex, rating);
            }

            resultSet = preparedStatement.executeQuery();

            out.println("<html><head><title>Review Table Report</title></head><body>");
            out.println("<br /><b><center><font color=\"RED\"><H2>Review Table Report</H2></font>");
            out.println("</center><br />");

            // Text input boxes
            out.println("<form method=\"get\" action=\"reviewJdbc\">");
            out.println("Movie ID: <input type=\"text\" name=\"movieID\"><br>");
            out.println("Star Rating: <input type=\"number\" name=\"starRating\" min=\"1\" max=\"5\"><br>");
            out.println("<input type=\"submit\" value=\"Search\">");
            out.println("</form>");

            out.println("<center><table border=\"1\">");
            out.println("<tr BGCOLOR=\"#cccccc\">");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Review ID</td>");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Review Description</td>");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Star Rating</td>");
            out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">Movie ID</td>");
            out.println("</tr>");

            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + resultSet.getString(1) + "</td>");
                out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + resultSet.getString(2) + "</td>");
                out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + resultSet.getInt(3) + "</td>");
                out.println("<td align = \"justify\"><font face =\"times new roman\"  size=\"4pt\">" + resultSet.getString(4) + "</td>");
                out.println("</tr>");
            }

            out.println("</table></CENTER>");
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (con != null) con.close();
                System.out.println("Connection is closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
