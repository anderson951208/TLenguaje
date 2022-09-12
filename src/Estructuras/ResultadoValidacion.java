
package Estructuras;

/**
 * Esta clase representa los datos obtenidos
 * de un proceso de validacion.
 */
public abstract class ResultadoValidacion {
   
    /**
     * Automata asociado al resultado.
     */
    protected Automata automata;
   
    /**
     * Cadena de entrada asociada al
     * resultado de validacion.
     */
    protected String entrada;
   
    /**
     * Simbolos de entrada que no pudieron
     * ser consumidos.
     */
    protected String entradaFaltante;

    /**
     * Retorna automata
     */
    public Automata getAutomata() {
        return automata;
    }

    /**
     * Retorna cadena de entrada.
     */
    public String getEntrada() {
        return entrada;
    }

    /**
     * Retorna conjunto de estados
     * alcanzados en una validacion.
     * (el camino estados).
     */
    public abstract Conjunto getCamino();

    /**
     * retorna simbolos en entrada que no
     * puediron ser cosumidos.
     */
    public String getEntradaFaltante() {
        return entradaFaltante;
    }
   
    /**
     * Determina si el resultado
     * es valido o no.
     */
    public abstract boolean esValido();
}
