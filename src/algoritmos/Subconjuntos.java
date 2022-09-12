package algoritmos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import analisis.Alfabeto;
import Estructuras.AFD;
import Estructuras.Automata;
import Estructuras.Conjunto;
import Estructuras.Estado;
import Estructuras.Transicion;

/**
 * Esta clase implementa los algoritmos para realizar
 * la conversion de un AFN a un AFD.
 */
public class Subconjuntos {
   
    

    /**
     * Realiza la conversion de un AFN a un AFD.
     */
    public static AFD getAFD(Automata afn) {
        Estado estadoOrigen, estadoDestino;
       
        
        AFD afd = new AFD(afn.getAlfabeto(), afn.getExprReg());
       
        
        Conjunto<Conjunto<Estado>> estadosD = new Conjunto<Conjunto<Estado>>();
       
        
        Queue<Conjunto<Estado>> colaTemp = new LinkedList<Conjunto<Estado>>();
       
        
        int estadosProcesados = 0;
        
        Conjunto<Estado> resultado = cerraduraEpsilon(afn.getEstadoInicial());
       
        
        estadosD.agregar(resultado);
        colaTemp.add(resultado);
       
        
        while (!colaTemp.isEmpty()) {
            
            Conjunto<Estado> T = colaTemp.remove();
            
            if (afd.cantidadEstados() < estadosD.cantidad())
                afd.agregarEstado(new Estado(afd.cantidadEstados()));
            
            estadoOrigen = afd.getEstado(estadosProcesados++);
            
            for (String simbolo : afn.getAlfabeto()) {
                
                Conjunto<Estado> M = mover(T, simbolo);
                Conjunto<Estado> U = cerraduraEpsilon(M);
                
                if (estadosD.contiene(U)) {
                    int posicion  = estadosD.obtenerPosicion(U);
                    estadoDestino = afd.getEstado(posicion);
                }
                else if (!U.estaVacio()) {
                    estadoDestino = new Estado(afd.cantidadEstados());
                    afd.agregarEstado(estadoDestino);
                    
                    estadosD.agregar(U);
                    colaTemp.add(U);
                }
                else {
                    
                    continue;
                }
               
                
                Transicion trans = new Transicion(estadoDestino, simbolo);
                estadoOrigen.getTransiciones().agregar(trans);
            }
            
        }
        
        for (int i=0; i < estadosD.cantidad(); i++) {
            Estado estadoAFD = afd.getEstado(i);
           
            for (Estado e : estadosD.obtener(i)) {
                if (e.getEsFinal()) {
                    estadoAFD.setEsFinal(true);
                    break;
                }
            }
        }
       
        afd.setEstadosD(estadosD);
        return afd;
    }
   
    /**
     * Retorna el conjunto de estados alcanzados.
     * (transicionEpisilon)
     */
    public static Conjunto<Estado> cerraduraEpsilon(Estado estado) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estado, resultado, Alfabeto.VACIO);
        resultado.ordenar();
        return resultado;
    }
   
    /**
     * Retorna el conjunto de estados alcanzados.
     * (transicionEpisilon)
     */
    public static Conjunto<Estado> cerraduraEpsilon(Conjunto<Estado> estados) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estados, resultado, Alfabeto.VACIO);
        resultado.ordenar();
        return resultado;
    }
    
    public static Conjunto<Estado> mover(Conjunto<Estado> estados, String simbolo) {
        Conjunto<Estado> resultado = new Conjunto<Estado>();
        recorrido(estados, resultado, simbolo);
        resultado.ordenar();
        return resultado;
    }
   
    /**
     * 
     * Realiza un recorrido del automata a partir de un Estado
     * inicial y pasa por todas las Transicions que coincidan
     * con el simbolo buscado.
     */
    private static void recorrido(Estado actual, Conjunto<Estado> alcanzados, String simboloBuscado) {
        
        Stack<Estado> pila = new Stack<Estado>();
        
        if (simboloBuscado.equals(Alfabeto.VACIO))
            alcanzados.agregar(actual);
        
        pila.push(actual);
       
        while (!pila.isEmpty()) {
            actual = pila.pop();
            for (Transicion t : actual.getTransiciones()) {
                Estado e = t.getEstado();
                String s = t.getSimbolo();
               
                if (s.equals(simboloBuscado) && !alcanzados.contiene(e)) {
                    alcanzados.agregar(e);
                    
                    if (simboloBuscado.equals(Alfabeto.VACIO))
                        pila.push(e);
                }
            }
        }
    }
   
    /**
     * Realiza un recorrido del automata a partir de un Estado
     * inicial y pasa por todas las Transicions que coincidan
     * con el simbolo buscado.
     */
    private static void recorrido(Conjunto<Estado> inicios, Conjunto<Estado> alcanzados, String simboloBuscado) {
        for (Estado e : inicios)
            recorrido(e, alcanzados, simboloBuscado);
    }
   
    
}

