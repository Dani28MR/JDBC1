import java.io.File;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out,true,"UTF-8"));
        System.out.println("Iniciando aplicación...");
        DBUtils.getConnection();
        //TareaDAO.addTarea(new Tarea("skaubda","aaaaaaa",true,LocalDate.now(),10));
        TareaDAO.obtenerTareas();
        DBDDL.crearTablaTarea();
        System.out.println("""
                            |----------------------------------|
                            | Bienvenido a tu gestor de tareas |
                            |----------------------------------|
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
                    for (Tarea tarea : TareaDAO.obtenerTareas()){
                        System.out.println(tarea.toString());
                    }
                }

                case 2 -> {
                    System.out.println("Introduce el nombre de la tarea:");
                    String nombre = new Scanner(System.in).nextLine();
                    System.out.println("Introduce la descripción de la tarea:");
                    String description = new Scanner(System.in).nextLine();
                    System.out.println("¿Está completada la tarea? (true/false):");
                    boolean completada = new Scanner(System.in).nextBoolean();
                    System.out.println("Introduce la fecha de la tarea (yyyy-MM-dd):");
                    LocalDate fechaTarea = LocalDate.parse(new Scanner(System.in).nextLine());
                    System.out.println("Introduce el nivel de importancia (1-10):");
                    int nivelImportancia = new Scanner(System.in).nextInt();

                    Tarea tarea = new Tarea(nombre,description,completada,fechaTarea,nivelImportancia);
                    TareaDAO.addTarea(tarea);
                }
                case 3 -> {
                    System.out.println("Introduce el id de la tarea a editar:");
                    int id = new Scanner(System.in).nextInt();
                    Tarea tarea = TareaDAO.getTareaById(id);
                    System.out.println("""
                            |-------------------------------|
                            | ¿Que desea editar?            |
                            |-------------------------------|
                            | 1. Nombre                     |
                            | 2. Descripción                |
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
                        case 6 -> System.out.println("");
                        default -> System.out.println("Opción no válida.");
                    }
                    TareaDAO.actualizarTarea(id,tarea);



                }
                case 4 -> {
                    System.out.println("Introduce el id de la tarea a eliminar:");
                    int id = new Scanner(System.in).nextInt();
                    TareaDAO.eliminarTarea(id);
                }
                case 5 -> {
                    System.out.println("Introduce el nombre de la tarea a buscar:");
                    String nombre = new Scanner(System.in).nextLine();
                    TareaDAO.buscarTareaPorNombre(nombre);
                }
                case 9 -> continuar=false;
                default -> System.out.println("Opción no válida.");

        }
    }



    }
}
