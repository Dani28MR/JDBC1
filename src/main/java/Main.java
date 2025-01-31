import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out,true,"UTF-8"));
        System.out.println("Iniciando aplicación...");

        DBUtils.getConnection();

        DBDDL.crearTablaTarea();

        //TareaDAO.addTarea(new Tarea("","",true,LocalDate.now(),10));
        TareaDAO.eliminarTarea(2);
        TareaDAO.obtenerTareas();

    }
}
