import java.time.LocalDate;

public class Tarea {
    private int id;
    private String nombre;
    private String description;
    private boolean completada;
    private LocalDate fechaTarea;
    private int nivelImportancia;

    public Tarea(String nombre, String description, boolean completada, LocalDate fechaTarea, int nivelImportancia) {
        this.nombre = nombre;
        this.description = description;
        this.completada = completada;
        this.fechaTarea = fechaTarea;
        this.nivelImportancia = nivelImportancia;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompletada() {
        return this.completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDate getFechaTarea() {
        return this.fechaTarea;
    }

    public void setFechaTarea(LocalDate fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public int getNivelImportancia() {
        return this.nivelImportancia;
    }

    public void setNivelImportancia(int nivelImportancia) {
        this.nivelImportancia = nivelImportancia;
    }

    @Override
    public String toString() {
        return "Tarea:" + '\n' +
                "ID -> " + id + '\n' +
                "Nombre -> " + nombre + '\n' +
                "Descripcion -> " + description + '\n' +
                "Completada -> " + completada+ '\n' +
                "Fecha de la tarea -> " + fechaTarea + '\n'+
                "Nivel de importancia -> " + nivelImportancia+ '\n' +
                '\n';
    }



}
