import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBUtils {
    private static Connection connection = null;
    private static final String dbUrl = leerArchivo().get("DB_URL");
    private static final String dbName = leerArchivo().get("DB");
    private static final String dbUser = leerArchivo().get("DB_USER");
    private static final String dbPassword = leerArchivo().get("DB_PASSWORD");
    private static final String SUPABASE= leerArchivo().get("SUPABASE");

    private static Map<String,String> leerArchivo(){
        Map<String,String> resultado = new HashMap<>();
        //Lectura del archivo

        try (BufferedReader br = new BufferedReader(new FileReader(".env"))) {
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
            System.out.println(e.getMessage());
        }


        return resultado;
    }

    public static Connection getConnection() {

        if (SUPABASE != null) {
            try {
                // Crear la conexión a la base de datos
                connection = DriverManager.getConnection(SUPABASE);
                //System.out.println("Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos.");
            }
        }else{
            // Verificar que todos los valores requeridos estén presentes
            if (dbUrl == null || dbName == null || dbUser == null || dbPassword == null) {
                throw new IllegalArgumentException("Faltan configuraciones en el archivo .env");
            }
            try {
                // Crear la conexión a la base de datos
                String fullUrl = dbUrl + dbName; // Combinar URL y nombre de la base de datos
                connection = DriverManager.getConnection(fullUrl, dbUser, dbPassword);
                //System.out.println("Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos.");
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection!= null &&!connection.isClosed()) {
                connection.close();
                System.out.println("Conexión cerrada exitosamente");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
        }
    }
}


