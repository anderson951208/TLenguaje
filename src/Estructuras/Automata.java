package Estructuras;

import analisis.Alfabeto;

/**
 * Clase que representa (AFD) o(AFN).
 */

public abstract class Automata {
   
    /**
     * Estados del automata.
     */
    protected Conjunto<Estado> estados;
   
    /**
     * ER para este automata.
     */
    protected String exprReg;
   
    /**
     * Alfabeto del automata.
     */
    protected Alfabeto alfabeto;
   
    /**
     * Constructor por defecto.
     */
    protected Automata() {
       this(null, "");
    }
   
    /**
     * Construye un Automata con un Alfabeto y ER.
     */
    protected Automata(Alfabeto alfabeto, String exprReg) {
        estados = new Conjunto<Estado>();
        setAlfabeto(alfabeto);
        setExprReg(exprReg);
    }
    
    /**
     * retorna el alfabeto.
     */
    
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    /**
     * Establece el Alfabeto del Automata.
     */
    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    /**
     * retorna la ER
     */
    public String getExprReg() {
        return exprReg;
    }

    /**
     * Establece la ER para este Automata
     */
    public void setExprReg(String exprReg) {
        this.exprReg = exprReg;
    }
   
    /**
     * retorna el Estado inicial
     */
    public Estado getEstadoInicial() {
        return estados.obtenerPrimero();
    }
   
    /**
     * retorna los Estados finales
     */
    public Conjunto<Estado> getEstadosFinales() {
        Conjunto<Estado> finales = new Conjunto<Estado>();
       
        for (Estado tmp : estados)
            if (tmp.getEsFinal())
                finales.agregar(tmp);
       
        return finales;
    }
   
    /**
     * Retorna los Estados No Finales
     */
    public Conjunto<Estado> getEstadosNoFinales() {
        Conjunto<Estado> noFinales = new Conjunto<Estado>();
       
        for (Estado tmp : estados)
            if (!tmp.getEsFinal())
                noFinales.agregar(tmp);
       
        return noFinales;
    }
   
    /**
     * 
     * Agrega un Estado al Automata.
     */
    public void agregarEstado(Estado estado) {
        estados.agregar(estado);
    }
   
    /**
     * Recupera un Estado deacuerdo a su posicion.
     */
    public Estado getEstado(int pos) {
        return estados.obtener(pos);
    }
   
    /**
     * Recupera Estados del Automata.
     */
    public Conjunto<Estado> getEstados() {
        return estados;
    }
   
    /**
     * retorna el numero de estados del Automata.
     */
    public int cantidadEstados() {
        return estados.cantidad();
    }
   
    /**
     * Establece en falso el estado de visitado de todos los
     * Estados del Automata.
     */
    public void iniciarRecorrido() {
        for (Estado tmp : estados)
            tmp.setVisitado(false);
    }
   
    @Override
    public String toString() {
        String str = "";
       
        for (Estado tmp : getEstados()) {
            str += tmp.toString();
           
            for (Transicion trans : tmp.getTransiciones())
                str += " --> " + trans.getEstado() + "{" + trans.getSimbolo() + "}";
           
            str += "\n";
        }
       
        return str;
    }
   
    /**
     * Copia los estados de un automata a otro.
     */
    public static void copiarEstados(Automata afOrigen, Automata afDestino, int incremento) {
        copiarEstados(afOrigen, afDestino, incremento, 0);
    }
   
    /**
     * Copia los estados de un automata a otro, omitiendo una cantidad
     * determinada del automata de origen.
     */
    public static void copiarEstados(Automata afOrigen, Automata afDestino,
                    int incrementoTrans, int omitidos) {
       
        int incrementoEst = incrementoTrans; 
        
        for (int i=omitidos; i < afOrigen.cantidadEstados(); i++)
            afDestino.agregarEstado(new Estado(afDestino.cantidadEstados()));
       
        /* Contador de omitidos */
        int contador = 0;
       
        /* Agregamos las transiciones de cada estado */
        for (Estado tmp : afOrigen.getEstados()) {
           
            if (omitidos > contador++)
                continue;
           
            /* Estado de afnDestino al cual se agregaran las transiciones */
            Estado objetivo = afDestino.getEstado(tmp.getIdentificador() + incrementoEst);
           
            /* Para cada estado, agregamos las transiciones */
            copiarTransiciones(afDestino, tmp.getTransiciones(), objetivo, incrementoTrans);
        }
    }
   
    /**
     * Copia las transiciones de un  AF a otro
     */
    public static void copiarTransiciones(Automata afDestino, Conjunto<Transicion> transiciones,
                        Estado objetivo, int incrementoTrans) {
       
        for (Transicion trans : transiciones) {
            int idDestino = trans.getEstado().getIdentificador();
            String simbolo = trans.getSimbolo();

            Estado estadoDestino = afDestino.getEstado(idDestino + incrementoTrans);
            Transicion nuevaTrans = new Transicion(estadoDestino, simbolo);

            objetivo.getTransiciones().agregar(nuevaTrans);
        }
    }
}
