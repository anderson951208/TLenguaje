/**
 * 
 */
package algoritmos;

import Estructuras.*;
import analisis.Alfabeto;

/**
 * Esta clase implementa los algoritmos de Thompson.
 */
public class Thompson {

    /**
     * Construye un AFN a partir de un simbolo, que puede ser
     * un simbolo del alfabeto o el simbolo vacio.
     */
    public static AFN basico(String simbolo) {
        AFN afn = new AFN();
       
        /* Estados inicial y final */
        Estado ini = new Estado(0);
        Estado fin = new Estado(1, true);
       
        /* Transicion entre los estados inicial y final */
        Transicion tran = new Transicion(fin, simbolo);
        ini.getTransiciones().agregar(tran);
       
        /* Agregamos los estados al AFN */
        afn.agregarEstado(ini);
        afn.agregarEstado(fin);
       
        return afn;
    }
   
    /**
     * Aplica la cerradura de Kleene (*) a un AFN.
     */
    public static AFN cerraduraKleene(AFN afn) {
        AFN afn_salida = new AFN();
               
        
        Estado nuevoInicio = new Estado(afn_salida.cantidadEstados());
        afn_salida.agregarEstado(nuevoInicio);
        
        Automata.copiarEstados(afn, afn_salida, afn_salida.cantidadEstados());
       
        
        Estado nuevoFin = new Estado(afn_salida.cantidadEstados(), true);
        afn_salida.agregarEstado(nuevoFin);
       
        
        nuevoInicio.getTransiciones().agregar(new Transicion(afn_salida.getEstado(1), Alfabeto.VACIO));
        nuevoInicio.getTransiciones().agregar(new Transicion(nuevoFin, Alfabeto.VACIO));
       
        
        Estado antesDeFinal = afn_salida.getEstado(afn_salida.cantidadEstados() - 2);
       
        
        antesDeFinal.getTransiciones().agregar(new Transicion(afn_salida.getEstado(1), Alfabeto.VACIO));
        antesDeFinal.getTransiciones().agregar(new Transicion(nuevoFin, Alfabeto.VACIO));
       
        return afn_salida;
    }
   
    /**
     * Aplica la cerradura positiva (+) a un AFN.
     */
    public static AFN cerraduraPositiva(AFN afn) {
        return concatenacion(afn, cerraduraKleene(afn));
    }
    
   
    /**
     * Aplica el operador de union a dos AFNs.
     */
    public static AFN union(AFN afn1, AFN afn2) {
        Transicion trans;
        AFN afn = new AFN();
        
        Estado nuevoInicio = new Estado(afn.cantidadEstados());
        afn.agregarEstado(nuevoInicio);
       
        
        Automata.copiarEstados(afn1, afn, afn.cantidadEstados());
       
        
        Automata.copiarEstados(afn2, afn, afn.cantidadEstados());
       
        
        Estado nuevoFin = new Estado(afn.cantidadEstados(), true);
        afn.agregarEstado(nuevoFin);
       
        
        trans = new Transicion();
        trans.setEstado(afn.getEstado(1));
        trans.setSimbolo(Alfabeto.VACIO);
        nuevoInicio.getTransiciones().agregar(trans);
       
        
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn1.cantidadEstados() + 1));
        trans.setSimbolo(Alfabeto.VACIO);
        nuevoInicio.getTransiciones().agregar(trans);
       
        
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn.cantidadEstados() - 1));
        trans.setSimbolo(Alfabeto.VACIO);
        afn.getEstado(afn1.cantidadEstados()).getTransiciones().agregar(trans);
       
        
        trans = new Transicion();
        trans.setEstado(afn.getEstado(afn.cantidadEstados() - 1));
        trans.setSimbolo(Alfabeto.VACIO);
        afn.getEstado(afn.cantidadEstados() - 2).getTransiciones().agregar(trans);
       
        return afn;
    }
   
    /**
     * Aplica el operador de concatenacion a dos AFNs.
     */
    public static AFN concatenacion(AFN afn1, AFN afn2) {
        AFN afn = new AFN();
       
        
        Automata.copiarEstados(afn1, afn, afn.cantidadEstados());
       
        
        Estado ultimoActual = afn.getEstado(afn.cantidadEstados() - 1);
       
        
        Automata.copiarEstados(afn2, afn, afn.cantidadEstados() - 1, 1);
       
        
        Estado inicioAfn2 = afn2.getEstadoInicial();
       
        
        Automata.copiarTransiciones(afn, inicioAfn2.getTransiciones(),
                            ultimoActual, ultimoActual.getIdentificador());
       
        
        afn.getEstado(afn.cantidadEstados() - 1).setEsFinal(true);
       
        return afn;
    }
}

