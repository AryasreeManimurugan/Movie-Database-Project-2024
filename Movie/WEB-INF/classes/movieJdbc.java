import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class movieJdbc extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {        
        Statement state4 = null;
        ResultSet result = null;
        String query="";        
        Connection con=null;

        String movieID = request.getParameter("movieid");
        String movieName = request.getParameter("moviename");
        String movieGenre = request.getParameter("genre");
        String movieLanguage = request.getParameter("language");
        String personID = request.getParameter("personID");

        try
        {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "CSI3450", "AryaMani1");
            System.out.println("Congratulations! You are connected successfully.");      
        }
        catch(SQLException e)
        {
            System.out.println("Error: "+e);
        }
        catch(Exception e)
        {
            System.err.println("Exception while loading driver");
        }
        try
        {
            state4 = con.createStatement();
        }
        catch (SQLException e)
        {
            System.err.println("SQLException while creating statement");
        }
       
        response.setContentType("text/html");
        PrintWriter out = null ;
        try
        {
            out =  response.getWriter();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       
        //query filtering stuff
        query = "select movieID, movieTitle, movieDescription, movieReleaseDate, movieLengthMin, movieGenre, movieRating, movieLanguage from MOVIE where 1=1";

        if(movieID != null && !movieID.isEmpty()) {
            query += " AND movieID = " + movieID;
        }

        if(movieName != null && !movieName.isEmpty()) {
            query += " AND movieTitle LIKE '%" + movieName + "%'";
        }

        if(movieGenre != null && !movieGenre.isEmpty()) {
            query += " AND movieGenre LIKE '%" + movieGenre + "%'";
        }

        if(movieLanguage != null && !movieLanguage.isEmpty()){
            query += " AND movieLanguage LIKE '%" + movieLanguage + "%'";
        }

        if (personID != null && !personID.isEmpty()) {
            query += " AND movieID IN (SELECT movieID FROM MOVIE_PERSON_ROLE WHERE personID = " + personID + ")";
        }
       

       

        out.println("<html><head><title>Movie Table Report</title>");
        out.println("<style>");
        out.println("table {width: 100%; border-collapse: collapse;}");
        out.println("th, td {border: 1px solid black; padding: 8px; text-align: left;}");
        out.println("th {background-color: #cccccc;}");
        out.println("</style>");
        out.println("</head><body>");
       
        out.println("<h2>Movie Table Report</h2>");
        out.println("<form action=\"movieJdbc\" method=\"get\">");
        out.println("Movie ID: <input type=\"text\" name=\"movieid\" value=\"" + (movieID != null ? movieID : "") + "\"> <br>");
        out.println("Movie Name: <input type=\"text\" name=\"moviename\" value=\"" + (movieName != null ? movieName : "") + "\"> <br>");
        out.println("Genre: <input type=\"text\" name=\"genre\" value=\"" + (movieGenre != null ? movieGenre : "") + "\"> <br>");
        out.println("Language: <input type=\"text\" name=\"language\" value=\"" + (movieLanguage != null ? movieLanguage : "") + "\"> <br>");
        out.println("Person ID: <input type=\"text\" name=\"personID\" value=\"" + (personID != null ? personID : "") + "\"> <br>");
        out.println("<input type=\"submit\" value=\"Search\">");
        out.println("</form>");
       
        try
        {
            result=state4.executeQuery(query);
        }
        catch (SQLException e)
        {
            System.err.println("SQLException while executing SQL Statement.");
        }

        out.println("<table>");
        out.println("<tr>");
        out.println("<th>Movie ID</th>");
        out.println("<th>Title</th>");
        out.println("<th>Description</th>");
        out.println("<th>Release Date</th>");
        out.println("<th>Duration</th>");
        out.println("<th>Genre</th>");
        out.println("<th>Rating</th>");
        out.println("<th>Language</th>");
        out.println("</tr>");

        try
        {
            while(result.next())
            {
                out.println("<tr>");
                out.println("<td>" + result.getString(1) + "</td>");
                out.println("<td>" + result.getString(2) + "</td>");
                out.println("<td>" + result.getString(3) + "</td>");
                out.println("<td>" + result.getString(4) + "</td>");
                out.println("<td>" + result.getString(5) + "</td>");
                out.println("<td>" + result.getString(6) + "</td>");
                out.println("<td>" + result.getString(7) + "</td>");
                out.println("<td>" + result.getString(8) + "</td>");
                out.println("</tr>");
            }
        }
        catch (SQLException e)
        {
            System.out.println("ResultSet is not connected");
        }

        out.println("</table>");
       
        try
        {
            result.close();
            state4.close();    
            con.close();
            System.out.println("Connection is closed successfully.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();    
        }

        out.println("</body></html>");
    }
}

