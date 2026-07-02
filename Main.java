import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       
        Servicios servicios = new Servicios("paquetes.csv", "camiones.csv");

        Paquete paquete= servicios.servicio1("PKG001");

        System.out.println("Paquete con ID: " + paquete.getId() + ", Código: " + paquete.getCodigo() + ", Peso: " + paquete.getPesoKg() + " kg, Contiene Alimento: " + paquete.contieneAlimento() + ", Nivel de Urgencia: " + paquete.getNivelUrgencia());
  
        List<Paquete> paquetesSinAlimentos = new ArrayList<>(servicios.servicio2(false));

        System.out.println("Paquetes sin alimentos:");
        for (Paquete p : paquetesSinAlimentos) {
            System.out.println(" - " + p.getId());
        }


       List<Paquete> paquetesEnRangoUrgencia = new ArrayList<>(servicios.servicio3(50, 90));

       System.out.println("Paquetes con nivel de urgencia entre 50 y 90:");
       for (Paquete p : paquetesEnRangoUrgencia) {
           System.out.println(" - " + p.getId());
       }

        // 2. BACKTRACKING
       
        Solucion resBack = servicios.backtracking(); //nos devuelve un objeto solucion

        System.out.println("BACKTRACKING:");
        //leemos el objeto
        System.out.println("Mejor Peso No Asignado: " + resBack.getPesoNoAsignado() + " kg");
        System.out.println("Estados Generados: " + resBack.getMetrica());
        System.out.println("Solución (IDs): " + Arrays.toString(resBack.getAsignaciones()));

        // 3. GREEDY
    
        Solucion resGreedy = servicios.greedy(); //nueva ejecucion, se reinicia la solucion y se devuelve un nuevo objeto con los resultados de greedy
        System.out.println("GREEDY:");

        //leemos el objeto nuevo
        System.out.println("Peso No Asignado: " + resGreedy.getPesoNoAsignado() + " kg");
        System.out.println("Candidatos Evaluados: " + resGreedy.getMetrica());
        System.out.println("Solución (IDs): " + Arrays.toString(resGreedy.getAsignaciones()));
    }
}
