import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDDL {

    public static void crearTablaTarea() {
        String sql = "CREATE TABLE IF NOT EXISTS tarea (" +
                "id SERIAL PRIMARY KEY, " +
                "nombre VARCHAR(20) NOT NULL, " +
                "description VARCHAR(200) NOT NULL, " +
                "completada BOOLEAN NOT NULL, " +
                "fechaTarea DATE NOT NULL, " +
                "nivelImportancia INT NOT NULL" +
                ");";

        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
            System.out.println("Tabla 'tarea' creada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al crear la tabla 'tarea'.");
        }
    }

}
