package Estructuras;


/**
 * esta clase representa las transiciones de un AF
 * por un simbolo a un estado destino.
 */
public class Transicion implements Comparable<Transicion> {
   
    private Estado estado;
   
    /**
     * Simbolo del alfabeto.
     */
    private String simbolo;

    /**
     * Construye una Transicion con atributos
     */
    public Transicion(Estado estado, String simbolo) {
        this.estado  = estado;
        this.simbolo = simbolo;
    }

    /**
     * Contruye una trnasicion con valores nulos
     */
    public Transicion() {
        this(null, null);
    }

    /**
     * Retorna el Estado destino de la Transicion.
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * asigna el estado destino de la transicion.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
   
    /**
     * Retona el simbolo de la transicion.
     */
    public String getSimbolo() {
        return simbolo;
    }
   
    /**
     * asigna el simbolo ala transicion.
     */
    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
   
    @Override
    public String toString() {
        return "(" + getEstado() + ", " + getSimbolo() + ")";
    }

    public int compareTo(Transicion obj) {
        Estado e1 = this.getEstado();
        Estado e2 = obj.getEstado();
       
        int diferencia = e1.getIdentificador() - e2.getIdentificador();
       
        if (diferencia != 0)
            return diferencia;
       
        String s1 = this.getSimbolo();
        String s2 = obj.getSimbolo();
       
        return s1.compareTo(s2);
    }
}
