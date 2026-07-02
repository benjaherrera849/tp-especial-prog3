import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Servicios{
   
    private ArrayList<Paquete> paquetes;
    private Map<String, Paquete> paquetesMap;
    private LinkedList<Paquete> paquetesConAlimentos;
    private LinkedList<Paquete> paquetesSinAlimentos;
    private Map<Integer, List<Paquete>> paquetesPorUrgencia;

    private ArrayList<Camion> camiones;

    private int cantidadPaquetes;
    private int cantidadCamiones;
  
   
   //complejidad: O(N + M), donde N es la cantidad de paquetes y M la cantidad de camiones.
   //se recorre el archivo de paquetes y se crean los objetos, luego se recorre el archivo de camiones y se crean los objetos, ambos recorridos son lineales respecto a la cantidad de elementos en cada archivo.
    public Servicios(String pathPaquetes, String pathCamiones) {
        this.paquetes = new ArrayList<>();
        this.paquetesMap = new HashMap<>();
        this.paquetesConAlimentos = new LinkedList<>();
        this.paquetesSinAlimentos = new LinkedList<>();
        this.paquetesPorUrgencia = new HashMap<>();
        this.camiones = new ArrayList<>();
        this.cantidadPaquetes = 0;
        this.cantidadCamiones = 0;
      
        
        leerPaquetes(pathPaquetes);
        leerCamiones(pathCamiones);
    }


//LECTORES DE ARCHIVOS

   private void leerPaquetes(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
            // primera linea, cantidad de elementos 
            // (preguntar si se puede crear el hashmap con cantidad de elementos en el constructor)
            String linea = br.readLine(); 
                    
              cantidadPaquetes = Integer.parseInt(linea.trim());

             //mientras haya elementos leo linea por linea
             for (int i = 0; i < cantidadPaquetes; i++) {

                 linea = br.readLine();
                 //cada atributo se separa por ";", entonces uso split para obtener
                 //  un array de Strings con cada atributo
                 String[] datos = linea.split(";");

              
                 int id = Integer.parseInt(datos[0]);
                 String codigo = String.valueOf(datos[1]);
                 double peso = Double.parseDouble(datos[2]);

                 //si es igual a 1 da true, cualquier otra cosa da false
                 boolean contieneAlimentos = datos[3].equals("1"); 
                 int urgencia = Integer.parseInt(datos[4]);
 
                 Paquete nuevoPaquete = new Paquete(id, codigo, peso, contieneAlimentos, urgencia);

                 this.paquetes.add(nuevoPaquete);

                 this.paquetesMap.put(codigo, nuevoPaquete);

                 // lleno las listas de paquetes con y sin alimentos
                if (contieneAlimentos) {
                     this.paquetesConAlimentos.add(nuevoPaquete);
                } else {
                    this.paquetesSinAlimentos.add(nuevoPaquete);
                }
                 
                // lleno el hashmap de paquetes por nivel de urgencia
                //llamo al getNivelUrgencia() de paquete ya que se controla que el nivel de urgencia este entre 1 y 100 en el constructor de Paquete, entonces no hace falta validar aca
                 if (!this.paquetesPorUrgencia.containsKey(nuevoPaquete.getNivelUrgencia())) {
                    this.paquetesPorUrgencia.put(nuevoPaquete.getNivelUrgencia(), new ArrayList<>());
               }
                 this.paquetesPorUrgencia.get(nuevoPaquete.getNivelUrgencia()).add(nuevoPaquete);

            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error leyendo el archivo de paquetes: " + e.getMessage());
        }
    }



    private void leerCamiones(String path) {

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            
             String linea = br.readLine(); 
             cantidadCamiones = Integer.parseInt(linea.trim());

             //mientras haya elementos leo linea por linea
               for (int i = 0; i < cantidadCamiones; i++) {

                 linea = br.readLine();

                 //cada atributo se separa por ";", entonces uso split para obtener
                 //  un array de Strings con cada atributo
                 String[] datos = linea.split(";");

              
                 int id = Integer.parseInt(datos[0]);
                 String patente = String.valueOf(datos[1]);
                 //igual que antes, si es igual a 1 da true, cualquier otra cosa da false
                 boolean refrigerado = datos[2].equals("1");
                 double capacidadKg = Double.parseDouble(datos[3]);
             

                 Camion nuevoCamion = new Camion(id, patente, refrigerado, capacidadKg);

                 this.camiones.add(nuevoCamion);

                }
        } catch (IOException | NumberFormatException e) {
            
            System.err.println("Error leyendo el archivo de camiones: " + e.getMessage());
        }
    }

   //PRIMERA PARTE

    
    //complejidad O(1) porque el hashmap nos permite acceder directamente al paquete con su codigo sin necesidad de recorrer la lista de paquetes

    public Paquete servicio1(String codigo) {
        return this.paquetesMap.get(codigo);
    }

  

//servicio 2
   //complejidad: O(K), donde K es la cantidad de paquetes en la lista solicitada.
   //solo se itera K veces para instanciar la nueva LinkedList y copiar los punteros de memoria. 
   //en el peor escenario (donde todos los paquetes del sistema son de un mismo tipo), K = N, resultando en O(N).
   public List<Paquete> servicio2(boolean contieneAlimentos) {
        if (contieneAlimentos) {
            return new LinkedList<>(this.paquetesConAlimentos);
        } else {
            return new LinkedList<>(this.paquetesSinAlimentos);
        }
    }

   
    //servicio 3
    //complejidad: O(R + K), donde R es el tamaño del rango solicitado (max - min) y K la cantidad de paquetes encontrados.
    //tenemos el arreglo de urgencias con una lista de paquetes para cada nivel de urgencia, entonces iteramos solo sobre el rango solicitado (R) y luego copiamos la lista de los paquetes encontrados (K).

    public List<Paquete> servicio3(int min, int max) {
        ArrayList<Paquete> paquetesEnRango = new ArrayList<>();

        //uso min-1 para ajustar a los indices del array (urgencia 1 en indice 0)
        for (int i = min; i < max; i++) {
            if(this.paquetesPorUrgencia.containsKey(i)) {
                paquetesEnRango.addAll(this.paquetesPorUrgencia.get(i));
            }
        }
        return paquetesEnRango;
    }




    //PARTE 2

    //BACKTRACKING

    //por cada paquete se prueba asignarlo a cada camion disponible o no asignarlo

    //complejidad: O(M^N), donde M es la cantidad de camiones y N la cantidad de paquetes.
    //en el peor escenario, cada paquete puede ser asignado a cualquiera de los M camiones o no ser asignado, lo que genera M+1 opciones por paquete.
    //esto resulta en (M+1)^N combinaciones posibles a evaluar. La poda puede reducir significativamente el espacio de búsqueda en la práctica, pero en el peor caso sigue siendo exponencial.
    
   public Solucion backtracking(){

     //creo una solucion sin asignaciones, con el peso no asignado en infinito, y con la metrica en 0
     Solucion mejor=new Solucion(new int[this.cantidadPaquetes],Double.MAX_VALUE , 0);

     int[] solucionActual = new int[this.cantidadPaquetes]; // arreglo de asignación
    
     //arranca en 0 porque el peso no asignado se va sumando a medida que decidimos no asignar paquetes
     double pesoNoAsignadoActual = 0.0;
     //se puede pasar 0 como parametro directamente pero queria aclarar por que empezaba en 0

     backtracking(0, solucionActual, pesoNoAsignadoActual, mejor);

     return mejor;

    }

   
    
    private void backtracking(int indicePaquete, int[] asignacionActual, double pesoNoAsignadoActual, Solucion mejorSolucion) {

         //contamos cada estado generado
         mejorSolucion.setMetrica(mejorSolucion.getMetrica()+1);

   
         //poda: si el peso no asignado actual es mayor o igual al mejor peso no asignado encontrado hasta ahora,
         //  no tiene sentido seguir explorando esta rama, porque no vamos a encontrar una solucion mejor
         if (pesoNoAsignadoActual >= mejorSolucion.getPesoNoAsignado()) {
          return; 
        }

         //condicion de corte: ya recorrimos todos los paquetes

        if (indicePaquete == cantidadPaquetes) {

             //si recorrio todos los paquetes y no corto por la poda significa que encontro una solucion mejor

             mejorSolucion.setPesoNoAsignado(pesoNoAsignadoActual);;
        
             //copiamos la solucion actual en la mejor solucion

             mejorSolucion.setAsignaciones(asignacionActual.clone());
            return;
        }

   
     //agarro el paquete del indice actual 
         Paquete paqueteActual = this.paquetes.get(indicePaquete);
         double pesoPaquete = paqueteActual.getPesoKg();

     //primera opcion, pruebo asignar el paquete a cada camion disponible
       for (Camion camion : this.camiones) {
        
         // valido restricciones (peso y si es alimento/refrigerado)
           if (esAsignacionValida(camion, paqueteActual, camion.getCapacidadKg(), pesoPaquete)) {
            
             //si es valido modifico el estado actual (agrego el paquete al camion, asigno el id del camion en la solucion actual)
             camion.agregarCarga(pesoPaquete);
             asignacionActual[indicePaquete] = camion.getId();
            
             //llamada recursiva
             backtracking(indicePaquete + 1, asignacionActual, pesoNoAsignadoActual, mejorSolucion);
            
             //deshago los cambios
             camion.quitarCarga(pesoPaquete);
             asignacionActual[indicePaquete] = -1;
            }
       }

         //ultima opcion, no agrego al paquete
         asignacionActual[indicePaquete] = -1; 
    
         //si no lo añado el peso queda sin asignar, entonces lo sumo
         pesoNoAsignadoActual += pesoPaquete;

         backtracking(indicePaquete + 1, asignacionActual, pesoNoAsignadoActual, mejorSolucion); 
    
         pesoNoAsignadoActual -= pesoPaquete; //deshago el cambio para la siguiente iteración

         //me marca error? creo que no es necesario ya que no es variable global, pero por las dudas lo dejo para que quede claro que el peso no asignado actual vuelve a ser el mismo que antes de esta llamada recursiva
     
    }




    private boolean esAsignacionValida(Camion camion, Paquete paqueteActual, double capacidadActual, double pesoPaquete) {

     //paso la capacidad y el peso por parametro ya que en el greedy no puedo usar directamente la capacidad del camion porque nunca deshago los cambios, tengo que manejarlo con un arreglo local

        // verificamos si el paquete es un alimento y el camión no es refrigerado
        if (paqueteActual.contieneAlimento() && !camion.isRefrigerado()) {
            return false;
        }

        // verificamos si el camión tiene suficiente capacidad para el paquete
        if (capacidadActual < pesoPaquete) {
            return false;
        }

         return true; // si pasa todas las validaciones, la asignación es válida
    }



    //GREEDY

    //se ordenan los paquetes de mayor a menor peso para intentar aprovechar al maximo la capacidad de los camiones y minimizar la chance de que queden paquetes livianos en camiones con mucha capacidad
    //Se ordenan los camiones dejando ultimos a los refrigerados para minimizar la chance de que un paquete no alimento quede en un camion refrigerado, si son no alimentos se agregan en los primeros (si es que el camion tiene suficiente capacidad), y si son alimentos van a recorrer los camiones hasta que encuentren uno refrigerado

    //complejidad: O(N log N + M log M + N * M) si contamos los ordenamientos
    // Siendo N = cantidad de paquetes y M = cantidad de camiones.

    // justificaion:
    // ordenar paquetes: O(N log N). el timsort y fusiona la memoria para ordenar los N elementos por peso.
    // ordenar camiones: O(M log M). timsort acomoda los M camiones enviando los false (0) al principio.
    // asignacion: O(N * M). En el peor escenario, por cada uno de los N paquetes el procesador tiene 
    // que recorrer secuencialmente los M camiones, aunque es un caso muy pesimista

   public Solucion greedy() {

    //reiniciamos la solucion
    int candidatosConsiderados = 0;  //al no ser recursivo no necesita ser variable global
    int mejorPesoNoAsignado = 0;
    int[]arregloSolucion = new int[cantidadPaquetes]; 

    //copiamos las listas para no modificar las originales
    List<Paquete> paquetesGreedy = new ArrayList<>(this.paquetes);
    List<Camion> camionesGreedy = new ArrayList<>(this.camiones);

    //ordenamiento por timsort
    //ordenamos los paquetes de mayor a menor peso, si p2 es mayor que p1, la diferencia es positiva entonces los cambia de lugar, p1 queda antes
    paquetesGreedy.sort((p1, p2) -> Double.compare(p2.getPesoKg(), p1.getPesoKg()));

    //ordenamos los camiones segun si estan refrigerados o no, si c2 no esta refrigerado (0), y c1 si (1), la diferencia es positiva entonces los cambia de lugar
    camionesGreedy.sort((c1, c2) -> Boolean.compare(c1.isRefrigerado(), c2.isRefrigerado()));

    //arreglo de capacidades locales para no modificar los camiones originales, se actualiza a medida que asignamos paquetes
    //tiene que estar despues del sort para que los indices coincidan
    double[] capacidadesLocales = new double[camionesGreedy.size()];
    
    for (int i = 0; i < camionesGreedy.size(); i++) {
        capacidadesLocales[i] = camionesGreedy.get(i).getCapacidadKg();
    }

    //puntero para recorrer los paquetes con while
    Iterator<Paquete> iteradorPaquetes = paquetesGreedy.iterator();

    while (iteradorPaquetes.hasNext()) {
        
        candidatosConsiderados++; //actualizo la metrica

        //agarro el paquete del arreglo ordenado y lo marco como no asignado
        Paquete p = iteradorPaquetes.next();
        double pesoPaquete = p.getPesoKg();
        boolean asignado = false;
        
        //uso for para poder usar el indice j para acceder al arreglo de capacidades locales, y no modificar los camiones originales
        for (int j = 0; j < camionesGreedy.size(); j++) {
            
            Camion c = camionesGreedy.get(j);
            candidatosConsiderados++; 
            
            //valido contra el arreglo usando el puntero fisico j
            if(esAsignacionValida(c, p, capacidadesLocales[j], pesoPaquete)) {

                capacidadesLocales[j] -= pesoPaquete; 
                arregloSolucion[p.getId()-1] = c.getId(); 
                //asigno el id del camion en la solucion, el -1 es porque los ids empiezan en 1 y los indices en 0
                //si simplemente uso i, estaria asignando el indice del paquete en la lista ordenada, que no necesariamente coincide con su id/indice original
                asignado = true; //marca como asignado
                break; //corta el for para no gastar ram buscando sin sentido
            } 
        }
            
        // si el paquete no se asigno, le pongo valor -1 en la solucion y sumo su peso al peso no asignado
        if (!asignado) {
            arregloSolucion[p.getId()-1] = -1;
           mejorPesoNoAsignado += pesoPaquete; 
        }
    }
        
    return new Solucion(arregloSolucion, mejorPesoNoAsignado, candidatosConsiderados);
}

}

     






 

