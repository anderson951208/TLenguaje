
package Estructuras;

/**
 * Esta clase representa un Automata Finito Deterministico con estados minimos (AFDMin)
 * se genera apartir de un AFD por el algoritmo de minimizacion.
 */
public class AFDMin {
    /**
     * AFD original.
     */
    private AFD afdOriginal;
   
    /**
     * AFD despues de aplicar eliminacion de estados inalcanzables.
     */
    private AFD afdPostInalcanzables;
   
    /**
     * AFD despues de aplicar minimizacion
     */
    private AFD afdPostMinimizacion;
   
    /**
     * AFD despues de aplicar la
     * eliminacion de estados identidades
     * no finales.
     */
    private AFD afdPostIdentidades;
   
    /**
     * contructor de un AFDMin
     */
    public AFDMin(AFD afdOriginal, AFD afdPostInalcanzables, AFD afdPostMinimizacion, AFD afdPostIdentidades) {
        this.afdOriginal          = afdOriginal;
        this.afdPostInalcanzables = afdPostInalcanzables;
        this.afdPostMinimizacion  = afdPostMinimizacion;
        this.afdPostIdentidades   = afdPostIdentidades;
    }
   
    public AFD getAfdOriginal() {
        return afdOriginal;
    }

    public AFD getAfdPostInalcanzables() {
        return afdPostInalcanzables;
    }

    public AFD getAfdPostMinimizacion() {
        return afdPostMinimizacion;
    }

    public AFD getAfdPostIdentidades() {
        return afdPostIdentidades;
    }
   
    /**
     * Verifica si la eliminacion de estados inalcanzables produjo algún
     * cambio sobre el AFD original.
     */
    public boolean inalcanzablesEliminados() {
        if (afdOriginal.toString().equals(afdPostInalcanzables.toString()))
            return false;
        else
            return true;
    }
   
    /**
     * Verifica si la eliminacion de estados identidades produjo algún
     * cambio sobre el AFD resultante de la minimizacion.
     */
    public boolean identidadesEliminados() {
        if (afdPostMinimizacion.toString().equals(afdPostIdentidades.toString()))
            return false;
        else
            return true;
    }
}
