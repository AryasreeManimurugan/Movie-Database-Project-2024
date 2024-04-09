import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class movie_person_role_reg extends HttpServlet {
  
  private PreparedStatement pstmtMoviePersonRole;
  private PreparedStatement pstmtRole;

  public void init() throws ServletException {
    initializeJdbc();
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String movieID = request.getParameter("movieID");
    String personID = request.getParameter("personID");
    String roleID = request.getParameter("roleID");
    String roleName = request.getParameter("roleName");

    try {
      if (movieID.isEmpty() || personID.isEmpty() || roleID.isEmpty() || roleName.isEmpty()) {
        out.println("All fields are required");
        return;
      }

      storeRole(roleID, roleName); // Insert role without checking existence
      storeMoviePersonRole(movieID, personID, roleID);

      out.println("Movie-Person-Role and Role added successfully");
    }
    catch(Exception ex) {
      out.println("\n Error: " + ex.getMessage());
    }
    finally {
      out.close();
    }
  }

  private void initializeJdbc() {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");

      String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
      String user = "CSI3450";
      String password = "AryaMani1";

      Connection conn = DriverManager.getConnection(url, user, password);

      pstmtMoviePersonRole = conn.prepareStatement("insert into Movie_Person_Role (movieID, personID, roleID) values (?, ?, ?)");
      pstmtRole = conn.prepareStatement("insert into Role (roleID, roleName) values (?, ?)");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void storeMoviePersonRole(String movieID, String personID, String roleID) throws SQLException {
    pstmtMoviePersonRole.setString(1, movieID);
    pstmtMoviePersonRole.setString(2, personID);
    pstmtMoviePersonRole.setString(3, roleID);

    pstmtMoviePersonRole.executeUpdate();
  }

  private void storeRole(String roleID, String roleName) throws SQLException {
    pstmtRole.setString(1, roleID);
    pstmtRole.setString(2, roleName);

    pstmtRole.executeUpdate();
  }
}
