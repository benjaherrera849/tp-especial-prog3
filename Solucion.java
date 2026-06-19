public class Solucion {
    private int[] asignaciones;
    private double pesoNoAsignado;
    private int metrica; // estados o candidatos

     public Solucion(int[] asignaciones, double pesoNoAsignado, int metrica) {
         this.asignaciones = asignaciones;
         this.pesoNoAsignado = pesoNoAsignado;
         this.metrica = metrica;
    }

     public double getPesoNoAsignado() { 
         return this.pesoNoAsignado; 
    }

     public int getMetrica() {
         return this.metrica;
    }

    
     public int[] getAsignaciones() {
         if (this.asignaciones == null) return null;
         int[] copia = new int[this.asignaciones.length];
         for (int i = 0; i < this.asignaciones.length; i++) {
            copia[i] = this.asignaciones[i];
         }
         return copia;
   }

     public void setAsignaciones(int[] asignaciones) {
         this.asignaciones = asignaciones;
     }

     public void setPesoNoAsignado(double pesoNoAsignado) {
         this.pesoNoAsignado = pesoNoAsignado;
     }

     public void setMetrica(int metrica) {
         this.metrica = metrica;
     }

   
}