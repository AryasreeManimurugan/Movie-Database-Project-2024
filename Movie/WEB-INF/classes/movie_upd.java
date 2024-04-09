import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_upd extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    Connection con = null;
    Statement state4 = null;

    String movieID = request.getParameter("movieID");
    String movieTitle = request.getParameter("movieTitle");
    String movieDescription = request.getParameter("movieDescription");
    String movieLengthMin = request.getParameter("movieLengthMin");
    String movieReleaseDate = request.getParameter("movieReleaseDate");
    String movieGenre = request.getParameter("movieGenre");
    String movieRating = request.getParameter("movieRating");
    String movieLanguage = request.getParameter("movieLanguage");

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "CSI3450", "AryaMani1");

        String query = "update movie set movieTitle = ?, movieReleaseDate = ?, movieLengthMin = ?, movieRating = ?, movieGenre = ?, movieDescription = ? where movieID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, movieTitle);
        pstmt.setString(2, movieReleaseDate);
        pstmt.setString(3, movieLengthMin);
        pstmt.setString(4, movieRating);
        pstmt.setString(5, movieGenre);
        pstmt.setString(6, movieDescription);
        pstmt.setString(7, movieID);

        int rowsAffected = pstmt.executeUpdate();

        out.println("<html><head><title>Movie has been updated</title></head><body>");
        out.print("<br /><b><center><font color=\"BLACK\"><H2>One Record has updated</H2></font>");
        out.println("</center><br />");

        if (rowsAffected > 0) {
            out.println("Movie updated successfully");
        } else {
            out.println("No movie found with the provided movieID");
        }
    } catch (SQLException | ClassNotFoundException e) {
        out.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (state4 != null) {
                state4.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        out.println("</body></html>");
    }
}

}