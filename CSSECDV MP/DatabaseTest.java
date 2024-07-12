import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseTest {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Replace the below URL with your database file path if it's not in the project directory
            String url = "jdbc:sqlite:CSSECDV MP/database.db";

            connection = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            // Test query
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                // read the result set
                System.out.println("username = " + rs.getString("username"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (Exception e) {
                // connection close failed.
                System.out.println(e.getMessage());
            }
        }
    }
}
