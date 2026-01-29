import java.io.InputStream;
import java.util.Properties;
/*
Lecture du fichier de configuration
 */
public class ConfigReader {
    public String dbUrl;
    public String dbUser;
    public String dbPassword;
    public String dbDatabase;
    public ConfigReader(String confFile) {
        Properties props = new Properties();
        try (InputStream input =
                     ConfigReader.class.getClassLoader().getResourceAsStream(
                             confFile)){
//                             "config/config.properties")) {
            if (input == null) {
                System.out.println("Impossible de trouver le fichier config.properties");
                return;
            }
            props.load(input);
            dbUrl = props.getProperty("url");
            dbUser = props.getProperty("user");
            dbPassword = props.getProperty("password");
            dbDatabase = props.getProperty("database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}