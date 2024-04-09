import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class review_reg extends HttpServlet {
    // Prepared statement to store a review into the database
    private PreparedStatement pstmt;

    /** Initialize global variables */
    public void init() throws ServletException {
        initializeJdbc();
    }

    /** Process the HTTP Post request */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Obtain parameters from the client
        String reviewID = request.getParameter("reviewID");
        String reviewDescription = request.getParameter("reviewDescription");
        String starRating = request.getParameter("starRating");
        String movieID = request.getParameter("movieID");

        try {
            // Check if required fields are empty
            if (reviewID == null || reviewDescription == null || movieID == null ||
                reviewID.isEmpty() || reviewDescription.isEmpty() || movieID.isEmpty()) {
                out.println("Please fill in all required fields: Review ID, description, and movie ID");
                return; // End the method
            }

            // Validate starRating
            int rating = Integer.parseInt(starRating);
            if (rating < 1 || rating > 5) {
                out.println("Star rating should be between 1 and 5");
                return; // End the method
            }

            // Store review in the database
            storeReview(reviewID, reviewDescription, starRating, movieID);

            out.println(reviewID + " is now registered in the database");
        } catch (NumberFormatException ex) {
            out.println("Star rating should be a valid number");
        } catch (SQLException ex) {
            out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            out.close(); // Close PrintWriter
        }
    }

    /** Initialize database connection */
    private void initializeJdbc() {
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection to the database
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            String user = "CSI3450";
            String password = "AryaMani1";
            Connection conn = DriverManager.getConnection(url, user, password);

            // Prepare the SQL statement
            pstmt = conn.prepareStatement("INSERT INTO REVIEW (reviewID, reviewDescription, starRating, movieID) VALUES (?, ?, ?, ?)");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Store a review record to the database */
    private void storeReview(String reviewID, String reviewDescription, String starRating, String movieID) throws SQLException {
        pstmt.setString(1, reviewID);
        pstmt.setString(2, reviewDescription);
        pstmt.setString(3, starRating);
        pstmt.setString(4, movieID);

        pstmt.executeUpdate();
    }
}
