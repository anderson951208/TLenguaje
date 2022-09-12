package Estructuras;

import analisis.Alfabeto;

/**
 * Esta clase representa un Automata Finito Deterministico (AFD).
 */
public class AFD extends Automata {
   
    /**
     * Conjunto de estados del AFN contenidos
     * en cada uno de los estados de este AFD.
     */
    private Conjunto<Conjunto<Estado>> estadosD;
   
    /**
     * Constructor por defecto.
     */
    public AFD() {
       this(null, "");
    }
   
    /**
     * crea un AFD con un determinado Alfabeto
     * y un String que seria la expresion regular.
     */
    public AFD(Alfabeto alfabeto, String exprReg) {
        super(alfabeto, exprReg);
        estadosD = null;
    }
   
    /**
     * Retorna el conjunto de estados
     */
    public Conjunto<Conjunto<Estado>> getEstadosD() {
        return estadosD;
    }

    /**
     * Asigna valor a con junto de estados
     */
    public void setEstadosD(Conjunto<Conjunto<Estado>> estadosD) {
        this.estadosD = estadosD;
    }
   
    /**
     * escribe 
     */
    public String estadosDtoString() {
        String str = "";
       
        for (int i=0; i < estadosD.cantidad(); i++) {
            Conjunto<Estado> conj = estadosD.obtener(i);
            Estado actual = getEstado(i);
           
            str += actual + " --> " + conj + "\n";
        }
       
        return str;
    }
}

