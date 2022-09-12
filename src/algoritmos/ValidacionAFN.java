
package algoritmos;

import Estructuras.AFN;
import Estructuras.Conjunto;
import Estructuras.Estado;
import Estructuras.Par;
import Estructuras.ResultadoValidacion;

/**
 * Esta clase representa el proceso de validacion
 * de una cadena de entrada contra un AFN.
 */
public class ValidacionAFN extends ResultadoValidacion {
   
    /**
     * Camino producido por las transiciones.
     */
    private final Conjunto<Par<Conjunto<Estado>, String>> camino;
   
    /**
     * Construye el resultado de una validacion de
     * una cadena de entrada con un automata.
     */
    public ValidacionAFN(AFN automata, String entrada,
        Conjunto<Par<Conjunto<Estado>, String>> camino, String entradaFaltante) {
       
        this.automata = automata;
        this.entrada = entrada;
        this.camino = camino;
        this.entradaFaltante = (entradaFaltante == null) ? "" : entradaFaltante;
    }
   
    /**
     * Retorna el camino producido
     * por las transiciones.
     */
    public Conjunto<Par<Conjunto<Estado>, String>> getCamino() {
        return camino;
    }
   
    /**
     * Determina el resultado de la validacion.
     */
    public boolean esValido() {
        if (!entradaFaltante.equals(""))
            return false;
       
        for (Estado e : camino.obtenerUltimo().getPrimero())
            if (e.getEsFinal())
                return true;
       
        return false;
    }
}
