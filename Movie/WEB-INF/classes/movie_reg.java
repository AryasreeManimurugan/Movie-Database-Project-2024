import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movie_reg extends HttpServlet {
  // Use a prepared statement to store a movie into the database
  private PreparedStatement pstmt;
  private Connection conn;

  public void init() throws ServletException {
      initializeJdbc();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();

      String movieID = request.getParameter("movieID");
      String movieTitle = request.getParameter("movieTitle");
      String movieDescription = request.getParameter("movieDescription");
      String movieLengthMin = request.getParameter("movieLengthMin");
      String movieReleaseDate = request.getParameter("movieReleaseDate");
      String movieGenre = request.getParameter("movieGenre");
      String movieRating = request.getParameter("movieRating");
      String movieLanguage = request.getParameter("movieLanguage");

      try {
          if (movieID.isEmpty() || movieTitle.isEmpty()) {
              out.println("Please provide both Movie ID and Title.");
              return;
          }

          storeMovie(movieID, movieTitle,  movieDescription, movieLengthMin, movieReleaseDate, movieGenre, movieRating, movieLanguage);

          out.println(movieID + " " + movieTitle + " is now registered in the database");
      } catch (SQLException ex) {
          out.println("Error: " + ex.getMessage());
      } finally {
          out.close();
      }
  }

  private void initializeJdbc() {
      try {
          String driver = "oracle.jdbc.driver.OracleDriver";
          Class.forName(driver);

          String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
          String user = "CSI3450";
          String password = "AryaMani1";

          conn = DriverManager.getConnection(url, user, password);

          pstmt = conn.prepareStatement("insert into MOVIE " +
                  "(movieID, movieTitle, movieDescription, movieLengthMin, movieReleaseDate, movieGenre, movieRating, movieLanguage) " +
                  "values (?, ?, ?, ?, ?, ?, ?, ?)");
      } catch (Exception ex) {
          ex.printStackTrace();
          System.out.println("Error");
      }
  }

  private void storeMovie(String movieID, String movieTitle, String movieDescription, String movieLengthMin, String movieReleaseDate, String movieGenre, String movieRating, String movieLanguage) throws SQLException {
      pstmt.setString(1, movieID);
      pstmt.setString(2, movieTitle);
      pstmt.setString(3, movieDescription);
      pstmt.setString(4, movieLengthMin);
      pstmt.setString(5, movieReleaseDate);
      pstmt.setString(6, movieGenre);
      pstmt.setString(7, movieRating);
      pstmt.setString(8, movieLanguage);
      pstmt.executeUpdate();
  }
}
