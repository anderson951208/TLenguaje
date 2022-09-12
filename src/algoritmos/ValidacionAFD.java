
package algoritmos;


import Estructuras.AFD;
import Estructuras.Conjunto;
import Estructuras.Estado;
import Estructuras.Par;
import Estructuras.ResultadoValidacion;

/**
 * Esta clase representa el proceso de validacion
 * de una cadena de entrada contra un AFD.
 */
public class ValidacionAFD extends ResultadoValidacion {
   
    /**
     * Camino producido por las transiciones.
     */
    private final Conjunto<Par<Estado, String>> camino;
   
    /**
     * Construye el resultado de una validacion de
     * una cadena de entrada con un automata.
     */
    public ValidacionAFD(AFD automata, String entrada,
        Conjunto<Par<Estado, String>> camino, String entradaFaltante) {
       
        this.automata = automata;
        this.entrada = entrada;
        this.camino = camino;
        this.entradaFaltante = (entradaFaltante == null) ? "" : entradaFaltante;
    }
   
    /**
     * Retorna el camino producido
     * por las transiciones.
     */
    public Conjunto<Par<Estado, String>> getCamino() {
        return camino;
    }
   
    /**
     * Determina el resultado de la validacion
     */
    public boolean esValido() {
        if (!entradaFaltante.equals(""))
            return false;
       
        if (camino.obtenerUltimo().getPrimero().getEsFinal())
            return true;
        else
            return false; 
    }
}

