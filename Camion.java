public class Camion {
    private int id;
    private String patente;
    private boolean refrigerado;
    private double capacidadKg;


    public Camion(int id, String patente, boolean refrigerado, double capacidadKg) {
        this.id = id;
        this.patente = patente;
        this.refrigerado = refrigerado;
        this.capacidadKg = capacidadKg;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getPatente() {
        return patente;
    }


    public void setPatente(String patente) {
        this.patente = patente;
    }


    public boolean isRefrigerado() {
        return refrigerado;
    }


    public void setRefrigerado(boolean refrigerado) {
        this.refrigerado = refrigerado;
    }


    public double getCapacidadKg() {
        return capacidadKg;
    }


    public void setCapacidadKg(double capacidadKg) {
        this.capacidadKg = capacidadKg;
    }

    public void agregarCarga(double pesoKg) {
       this.capacidadKg -= pesoKg;
    }

    public void quitarCarga(double pesoKg) {
       this.capacidadKg += pesoKg;
    }

    

    
}
