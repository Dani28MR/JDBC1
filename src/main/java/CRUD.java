import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CRUD {

    // Método para añadir una tarea a la tabla
    public static void addTarea(Tarea tarea) {
        String sql = "INSERT INTO tarea (nombre, description, completada, fechaTarea, nivelImportancia) " +
                "VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tarea.getNombre());
            preparedStatement.setString(2, tarea.getDescription());
            preparedStatement.setBoolean(3, tarea.isCompletada());
            preparedStatement.setDate(4, Date.valueOf(tarea.getFechaTarea()));
            preparedStatement.setInt(5, tarea.getNivelImportancia());

            preparedStatement.executeUpdate();
            System.out.println("Tarea añadida correctamente.");
            obtenerTareas();

        } catch (SQLException e) {
            System.out.println("Error al añadir la tarea.");
        }
    }

    // Método para leer todas las tareas de la tabla
    public static List<Tarea> obtenerTareas() {
        String sql = "SELECT * FROM tarea;";
        List<Tarea> tareas = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Tarea tarea = new Tarea(
                        resultSet.getString("nombre"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("completada"),
                        resultSet.getDate("fechaTarea").toLocalDate(),
                        resultSet.getInt("nivelImportancia")
                );
                tareas.add(tarea);
                tarea.setId(resultSet.getInt("id"));

            }

        } catch (SQLException e) {
            System.out.println("Error al obtener las tareas.");
        }

        return tareas;
    }

    // Método para actualizar una tarea
    public static void actualizarTarea(int id, Tarea tarea) {
        String sql = "UPDATE tarea SET nombre = ?, description = ?, completada = ?, fechaTarea = ?, nivelImportancia = ? " +
                "WHERE id = ?;";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, tarea.getNombre());
            preparedStatement.setString(2, tarea.getDescription());
            preparedStatement.setBoolean(3, tarea.isCompletada());
            preparedStatement.setDate(4, Date.valueOf(tarea.getFechaTarea()));
            preparedStatement.setInt(5, tarea.getNivelImportancia());
            preparedStatement.setInt(6, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Tarea actualizada correctamente.");
            } else {
                System.out.println("No se encontró la tarea con ID: " + id);
            }
            obtenerTareas();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la tarea.");
        }
    }

    // Método para eliminar una tarea
    public static void eliminarTarea(int id) {
        String sql = "DELETE FROM tarea WHERE id = ?;";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Tarea eliminada correctamente.");
            } else {
                System.out.println("No se encontró la tarea con ID: " + id);
            }
            obtenerTareas();

        } catch (SQLException e) {
            System.out.println("Error al eliminar la tarea.");
        }
    }


    public static Tarea getTareaById(int id) {
        String sql = "SELECT * FROM tarea WHERE id =?;";
        Tarea tarea = null;

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tarea = new Tarea(
                        resultSet.getString("nombre"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("completada"),
                        resultSet.getDate("fechaTarea").toLocalDate(),
                        resultSet.getInt("nivelImportancia")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener la tarea con ID: " + id);
        }
        obtenerTareas();

        return tarea;
    }

    public static List<Tarea> buscarTareaPorNombre(String nombre) {
        List<Tarea> tareaList = obtenerTareas();

        return tareaList.stream()
               .filter(tarea -> tarea.getNombre().equalsIgnoreCase(nombre))
               .collect(Collectors.toList());
    }

    public static List<Tarea> getTareasImprescindibles() {
        List<Tarea> tareaList = obtenerTareas();
        return tareaList.stream()
               .filter(tarea -> tarea.getNivelImportancia() == 10 )
               .collect(Collectors.toList());
    }

    public static List<Tarea> getTareasPendientes() {
        List<Tarea> tareaList = obtenerTareas();
        return tareaList.stream()
                .filter(tarea ->!tarea.isCompletada())
                .collect(Collectors.toList());
    }

    public static List<Tarea> getTareasExpiradas() {
        LocalDate fechaExpiracion = LocalDate.now();
        List<Tarea> tareaList = obtenerTareas();
        tareaList.removeIf(
                tarea -> tarea
                        .getFechaTarea()
                        .isAfter(fechaExpiracion) || tarea.isCompletada()
        );
        return tareaList;
    }
}
