import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
       
        Servicio servicios = new Servicio("paquetes.csv", "camiones.csv");

        // 2. BACKTRACKING
       
        Solucion resBack = servicios.ejecutarBacktracking(); //nos devuelve un objeto solucion

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
