import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_del extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String movieID = request.getParameter("movieID");
        String movieTitle = request.getParameter("movieTitle");

        // Logging to check parameter values
        System.out.println("movieID: " + movieID);
        System.out.println("movieTitle: " + movieTitle);

        Connection con = null;
        PreparedStatement deleteStatement = null;

        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");
            System.out.println("Congratulations! You are connected successfully.");

            // Check if there are related records in other tables
            String relatedQuery = "SELECT COUNT(*) FROM REVIEW r LEFT JOIN MOVIE_PERSON_ROLE mpr ON r.movieID = mpr.movieID WHERE r.movieID = ?";
            deleteStatement = con.prepareStatement(relatedQuery);
            deleteStatement.setString(1, movieID);
            ResultSet relatedResult = deleteStatement.executeQuery();
            relatedResult.next();
            int relatedCount = relatedResult.getInt(1);
            if (relatedCount > 0) {
                out.println("<p>Cannot delete movie record. Related records exist in other tables.</p>");
                return;
            }

             // Construct the delete query
            String deleteQuery = "DELETE FROM MOVIE WHERE movieID = ?";
            deleteStatement = con.prepareStatement(deleteQuery);
            deleteStatement.setString(1, movieID);

            // Execute the delete query
            int rowsDeleted = deleteStatement.executeUpdate();

            if (rowsDeleted > 0) {
                out.println("<p>Record deleted successfully.</p>");
            } else {
                out.println("<p>No record deleted.</p>");
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            e.printStackTrace();
            out.println("<p>Error occurred: " + e.getMessage() + "</p>");
        } finally {
            try {
                // Close resources
                if (deleteStatement != null) deleteStatement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
