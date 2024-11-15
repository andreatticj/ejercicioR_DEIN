import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBBDD {
    private static final String AWS_URL = "jdbc:mysql://andreaex.c9amcg4qezjd.us-east-1.rds.amazonaws.com:3306/";
    private static final String LOCAL_DOCKER_URL = "jdbc:mysql://localhost:33066/";
    private static final String USER = "admin";
    private static final String PASSWORD = "12345678";
    private static Connection connection;

    public static Connection getAWSConnection() {
        try {
            // Intentar conectarse sin especificar la base de datos (solo al servidor)
            connection = DriverManager.getConnection(AWS_URL, USER, PASSWORD);
            
            // Crear la base de datos 'BatallasGoT' si no existe
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS BatallasGoT");
            
            // Ahora conectarse a la base de datos creada
            connection = DriverManager.getConnection(AWS_URL + "BatallasGoT", USER, PASSWORD);
            return connection;
            
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos AWS: " + e.getMessage());
            e.printStackTrace();
        }
        return null;  // Si la conexión falla, retornamos null
    }

    public static Connection getDockerConnection() {
        try {
            // Intentar conectarse sin especificar la base de datos (solo al servidor)
            connection = DriverManager.getConnection(LOCAL_DOCKER_URL, USER, PASSWORD);
            
            // Crear la base de datos 'BatallasGoT' si no existe
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS BatallasGoT");
            
            // Ahora conectarse a la base de datos creada
            connection = DriverManager.getConnection(LOCAL_DOCKER_URL + "BatallasGoT", USER, PASSWORD);
            return connection;
            
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos Docker: " + e.getMessage());
            e.printStackTrace();
        }
        return null;  // Si la conexión falla, retornamos null
    }
}
