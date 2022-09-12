
package Estructuras;

import analisis.Alfabeto;


/**
 * Esta clase representa un Automata Finito No deterministico (AFN)
 * este se contruye con el algoritmo de thomson.
 */

public class AFN extends Automata {
       
    /**
     * Constructor por defecto.
     */
    public AFN() {
       super();
    }
   
    /**
     * Construye un AFN con un Alfabetob y una ER.
     */
    public AFN(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
    }
}

