/**
 * 
 */
package Estructuras;

import java.util.HashMap;

import analisis.Alfabeto;

/**
 * Esta clase representa un estado para un AF.
 */
public class Estado implements Comparable<Estado> {

    /**
     * Identificador de este Estado.
     */
    private int identificador;
   
    /**
     * Variable que indica si el Estado es final.
     */
    private boolean esFinal;
   
    /**
     * Etiqueta de este estado.
     */
    private String etiqueta;
   
    /**
     * Conjunto de transiciones del Estado.
     */
    private Conjunto<Transicion> transiciones;
   
    /**
     * Indica si este Estado ya fue visitado
     * durante algún recorrido.
     */
    private boolean visitado;
   
    /**
     * Crea un Estado que no es final con un identificador.
     */
    public Estado(int identificador) {
        this(identificador, false);
    }
   
    /**
     * Crea un Estado con un identificador,que puede ser final o no
     */
    public Estado(int identificador, boolean esFinal) {
        setIdentificador(identificador);
        setEsFinal(esFinal);
        setEtiqueta(String.valueOf(identificador));
        transiciones = new Conjunto<Transicion>();
    }
   
    /**
     * Establece el identificador para este Estado.
     */
    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }
   
    /**
     * retorna el identificador para este Estado.
     */
    public int getIdentificador() {
        return identificador;
    }
   
    /**
     * retorna la etiqueta del Estado.
     */
    public String getEtiqueta() {
        return etiqueta;
    }

    /**
     * Establece la etiqueta para el Estado.
     */
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
   
    /**
     * retorna true si el estado es de aceptacion.
     */
    public boolean getEsFinal() {
        return esFinal;
    }

    /**
     * dael valor de estado de aceptacion para el Estado.
     */ 
    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }
   
    /**
     * retorna el estado inicial.
     */
    public boolean getEsInicial() {
        return identificador == 0;
    }
   
    /**
     * retorna el conjunto de transiciones.
     */
    public Conjunto<Transicion> getTransiciones() {
        return transiciones;
    }
   
    /**
     * Obtiene una tabla hash con las transiciones de los estados
     * con los simbolos de un Alfabeto.
     */
    public HashMap<String, Estado> getTransicionesSegunAlfabeto(Alfabeto alfabeto) {
        HashMap<String, Estado> trans = new HashMap<String, Estado>();
       
        
        for (String s : alfabeto)
            trans.put(s, null);
       
        
        for (Transicion t : getTransiciones())
            trans.put(t.getSimbolo(), t.getEstado());
       
        return trans;
    }
   
    /**
     * retorna true si el estado ha sido visitado.
     */
    public boolean getVisitado() {
        return visitado;
    }
   
    /**
     * da el "estado" de visitado al estado.
     */
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
   
    /**
     * Verifica si el Estado, es un estado identidad.
     */
    public boolean getEsIdentidad() {
        for (Transicion trans : getTransiciones())
            if (!this.equals(trans.getEstado()))
                return false;
       
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
       
        if (getClass() != obj.getClass())
            return false;
       
        final Estado other = (Estado) obj;
        if (this.identificador == other.identificador)
            return true;
       
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.identificador;
        return hash;
    }

    public int compareTo(Estado obj) {
        return this.identificador - obj.identificador;
    }
   
    @Override
    public String toString() {
        String valor;
       
        if (getEtiqueta().equals(""))
            valor = String.valueOf(identificador);
        else
            valor = getEtiqueta();
       
        if (getEsInicial())
            valor += "i";
       
        if (getEsFinal())
            valor += "f";
               
        return valor;
    }
}

