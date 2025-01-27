import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBUtils {
    private static final String DB_URL = "";
    private static final String DB = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private static Map<String,String> leerArchivo(String archivo){
        Map<String,String> resultado = new HashMap<String,String>();
        //Lectura del archivo

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Ignorar líneas vacías
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Dividir línea en clave y valor
                String[] parts = line.split("=", 2); // Dividir por el primer '='
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    resultado.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return resultado;
    }

    public static Connection getConnection() {
        // Leer las configuraciones del archivo .env
        Map<String, String> config = leerArchivo(".env");

        // Obtener los valores necesarios
        String dbUrl = config.get("DB_URL");
        String dbName = config.get("DB");
        String dbUser = config.get("DB_USER");
        String dbPassword = config.get("DB_PASSWORD");

        // Verificar que todos los valores requeridos estén presentes
        if (dbUrl == null || dbName == null || dbUser == null || dbPassword == null) {
            throw new IllegalArgumentException("Faltan configuraciones en el archivo .env");
        }

        Connection connection = null;

        try {
            // Crear la conexión a la base de datos
            String fullUrl = dbUrl + dbName; // Combinar URL y nombre de la base de datos
            connection = DriverManager.getConnection(fullUrl, dbUser, dbPassword);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }

        return connection;
    }

}
