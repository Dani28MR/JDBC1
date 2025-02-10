import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out,true));
        System.out.println("Iniciando aplicación...");
        DBUtils.getConnection();
        CRUD.obtenerTareas();
        DBDDL.crearTablaTarea();
        System.out.print("""
                            |----------------------------------|
                            | Bienvenido a tu gestor de tareas |
                            """);
        boolean continuar = true;
        while (continuar) {
            System.out.println("""
                    |----------------------------------|
                    | ¿Qué quieres hacer?              |
                    |----------------------------------|
                    | 1. Ver tus tareas                |
                    | 2. Añadir una tarea              |
                    | 3. Editar una tarea              |
                    | 4. Eliminar una tarea            |
                    | 5. Buscar tarea por nombre       |
                    | 6. Ver tareas imprescindibles    |
                    | 7. Ver tareas pendientes         |
                    | 8. Ver tareas expiradas          |
                    | 9. Salir del programa            |
                    |----------------------------------|
                    """);
            int opcion = new Scanner(System.in).nextInt();
            switch(opcion){
                case 1 -> {
                    if (CRUD.obtenerTareas().isEmpty()){
                        System.out.println("No hay tareas para mostrar.");
                    }else {
                        for (Tarea tarea : CRUD.obtenerTareas()){
                            System.out.println(tarea.toString());
                        }
                    }
                }

                case 2 -> {
                    System.out.println("Introduce el nombre de la tarea:");
                    String nombre = new Scanner(System.in).nextLine();
                    System.out.println("Introduce la descripción de la tarea:");
                    String description = new Scanner(System.in).nextLine();
                    System.out.println("¿Está completada la tarea? (true/false):");
                    boolean completada = new Scanner(System.in).nextBoolean();
                    System.out.println("Introduce la fecha de expiracion de la tarea (yyyy-MM-dd):");
                    LocalDate fechaTarea = LocalDate.parse(new Scanner(System.in).nextLine());
                    System.out.println("Introduce el nivel de importancia (1-10):");
                    int nivelImportancia = new Scanner(System.in).nextInt();

                    Tarea tarea = new Tarea(nombre,description,completada,fechaTarea,nivelImportancia);
                    CRUD.addTarea(tarea);

                }

                case 3 -> {
                    System.out.println("Introduce el id de la tarea a editar:");
                    int id = new Scanner(System.in).nextInt();
                    Tarea tarea = CRUD.getTareaById(id);
                    System.out.println("""
                            |-------------------------------|
                            | ¿Que desea editar?            |
                            |-------------------------------|
                            | 1. Nombre                     |
                            | 2. Descripcion                |
                            | 3. Estado                     |
                            | 4. Fecha de tarea             |
                            | 5. Nivel de importancia       |
                            | 6. Volver                     |
                            |-------------------------------|
                            """);
                    int opcionEditar = new Scanner(System.in).nextInt();
                    switch(opcionEditar){
                        case 1 -> {
                            System.out.println("Introduce el nuevo nombre de la tarea:");
                            tarea.setNombre(new Scanner(System.in).nextLine());

                        }
                        case 2 -> {
                            System.out.println("Introduce la nueva descripción de la tarea:");
                            tarea.setDescription(new Scanner(System.in).nextLine());
                        }
                        case 3 -> {
                            System.out.println("¿Está completada la tarea? (true/false):");
                            tarea.setCompletada(new Scanner(System.in).nextBoolean());
                        }
                        case 4 -> {
                            System.out.println("Introduce la nueva fecha de la tarea (yyyy-MM-dd):");
                            tarea.setFechaTarea(LocalDate.parse(new Scanner(System.in).nextLine()));
                        }
                        case 5 -> {
                            System.out.println("Introduce el nuevo nivel de importancia (1-10):");
                            tarea.setNivelImportancia(new Scanner(System.in).nextInt());
                        }
                        case 6 -> System.out.println(" ");
                        default -> System.out.println("Opción no válida.");
                    }
                    CRUD.actualizarTarea(id,tarea);
                }

                case 4 -> {
                    System.out.println("Introduce el id de la tarea a eliminar:");
                    int id = new Scanner(System.in).nextInt();
                    CRUD.eliminarTarea(id);
                }

                case 5 -> {
                    System.out.println("Introduce el nombre de la tarea a buscar:");
                    String nombre = new Scanner(System.in).nextLine();
                    if (!CRUD.obtenerTareas().isEmpty()) {
                        for (Tarea tarea : CRUD.buscarTareaPorNombre(nombre)) {
                            System.out.println(tarea.toString());
                        }
                    }else{
                        System.out.println("No se ha encontrado niguna tarea con ese nombre");
                    }
                }

                case 6 -> {
                    if (!CRUD.getTareasImprescindibles().isEmpty()) {
                        for (Tarea tarea : CRUD.getTareasImprescindibles()){
                            System.out.println(tarea.toString());
                        }
                    }else{
                        System.out.println("No hay tareas imprescindibles");
                    }
                }

                case 7 -> {
                    if (!CRUD.getTareasPendientes().isEmpty()) {
                        for (Tarea tarea : CRUD.getTareasPendientes()){
                            System.out.println(tarea.toString());
                        }
                    }else{
                        System.out.println("No hay tareas pendientes");
                    }
                }

                case 8 -> {
                    if (!CRUD.getTareasExpiradas().isEmpty()) {
                        for (Tarea tarea : CRUD.getTareasExpiradas()){
                            System.out.println(tarea.toString());
                        }
                    }else{
                        System.out.println("No hay tareas expiradas");
                    }
                }

                case 9 -> {
                    System.out.println("Gracias por utilizar nuestro gestor de tareas. Hasta pronto!");
                    continuar=false;
                }

                default -> System.out.println("Opción no válida.");

            }
        }
    }
}
