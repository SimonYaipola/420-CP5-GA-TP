import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnexionDB {

    public static ConfigReader config = new ConfigReader("config/config.properties");
    private static final String database = config.dbDatabase;;
    private static final String URL = config.dbUrl + database;
    private static final String USER = config.dbUser;
    private static final String PASSWORD = config.dbPassword;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
