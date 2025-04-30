import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/charan";
    private static final String USER = "root";
    private static final String PASSWORD = "Charan#206#";
    private static Connection conn;

    private DBConnection() {}

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
