import java.sql.Connection;
import java.sql.DriverManager;

public class sqlConnection {

    public static Connection connectToDatabase(){
        // try and connect to the database
        try {
            //database details
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver).newInstance();
            String URL = "jdbc:mysql://127.0.0.1:3306/restaurant";
            String user = "root";
            String pass = "asdfghjkl";

            //create connection
            Connection con = DriverManager.getConnection(URL, user, pass);

            //return connection
            return con;
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

        return null;
    }
}
