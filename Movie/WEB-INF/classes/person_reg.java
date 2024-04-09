import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class person_reg extends HttpServlet{
    private PreparedStatement pstmt;

    /** Initialize global variables */
    public void init() throws ServletException {
        initializeJdbc();
    }

    /** Process the HTTP Post request */
    public void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Obtain parameters from the client
        String personID = request.getParameter("personID");
        String personName = request.getParameter("personName");
        String personGender = request.getParameter("personGender");
        String personDateOfBirth = request.getParameter("personDateOfBirth");
        String personSalary = request.getParameter("personSalary");
    

        try {

            //out.println("First Name: " + firstName );
            if (personID.length() == 0 || personName.length() == 0) {
                out.println("Please: person ID and name are required");
                return; // End the method
            }

            storeStudent(personID, personName, personGender, personDateOfBirth, personSalary);

            out.println(personID + " " + personName +
                    " is now registered in the database");
        }
        catch(Exception ex) {
            out.println("\n Error: " + ex.getMessage());
        }
        finally {
            out.close(); // Close stream
        }
    }

    /** Initialize database connection */
    private void initializeJdbc() {
        try {
            // Declare driver and connection string
            // String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
            // String connectionString = "jdbc:odbc:exampleMDBDataSource";
            // For MySQL
            // String driver = "com.mysql.jdbc.Driver";
            // String connectionString = "jdbc:mysql://localhost/test";
            // For Oracle
            String driver = "oracle.jdbc.driver.OracleDriver";

            // Load the driver
            Class.forName(driver);

            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            String user = "CSI3450";
            String password = "AryaMani1";

            // Connect to the sample database
            Connection conn = DriverManager.getConnection
                    (url,user, password);

            // Create a Statement
            pstmt = conn.prepareStatement("INSERT INTO MoviePerson " +
            "(personID, personName, personGender, personDateOfBirth, personSalary) VALUES (?, ?, ?, ?, ?)");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Store a student record to the database */
    private void storeStudent(String personID, String personName,
                              String personGender, String personDateOfBirth, String personSalary) throws SQLException {
        pstmt.setString(1, personID);
        pstmt.setString(2, personName);
        pstmt.setString(3, personGender);
        pstmt.setString(4, personDateOfBirth);
        pstmt.setString(5, personSalary);

        pstmt.executeUpdate();
    }
}