public class Paquete{

    private int id;
    private String codigo;
    private double pesoKg;
    private boolean contieneAlimento;
    private int nivelUrgencia;

    
    public Paquete(int id, String codigo, double pesoKg, boolean contieneAlimento, int nivelUrgencia) {
        this.id = id;
        this.codigo = codigo;
        this.pesoKg = pesoKg;
        this.contieneAlimento = contieneAlimento;
        
        if(nivelUrgencia>100 || nivelUrgencia < 1){
           this.nivelUrgencia = 1;
        }else{
            this.nivelUrgencia=nivelUrgencia;
        }
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getCodigo() {
        return codigo;
    }


    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public double getPesoKg() {
        return pesoKg;
    }


    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }


    public boolean contieneAlimento() {
        return contieneAlimento;
    }


    public void setContieneAlimento(boolean contieneAlimento) {
        this.contieneAlimento = contieneAlimento;
    }


    public int getNivelUrgencia() {
        return nivelUrgencia;
    }


    public void setNivelUrgencia(int nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }


    
}