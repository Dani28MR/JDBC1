import java.time.LocalDate;

public class Tarea {
    private String nombre;
    private String description;
    private boolean completada;
    private LocalDate fechaTarea;

    public Tarea(String nombre, String description, boolean completada, LocalDate fechaTarea) {
        this.nombre = nombre;
        this.description = description;
        this.completada = completada;
        this.fechaTarea = fechaTarea;
    }
}
