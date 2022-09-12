package algoritmos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import analisis.Alfabeto;
import Estructuras.AFD;
import Estructuras.AFDMin;
import Estructuras.Automata;
import Estructuras.Conjunto;
import Estructuras.Estado;
import Estructuras.Transicion;

/**
 * Esta clase implementa el algoritmo de minimizacion de estados de un AFD.
 */
public class Minimizacion {

	/**
         * Retorna un AFD minimo apartir de un AFD.
	 */
	public static AFDMin getAFDminimo(AFD afdOriginal) {

		/*se eliminana los estados inalcanzables */
		AFD afdPostInalcanzables = new AFD();
		copiarAutomata(afdOriginal, afdPostInalcanzables);
		eliminarInalcanzables(afdPostInalcanzables);

		/* Proceso de minimizacion */
		AFD afdPostMinimizacion = minimizar(afdPostInalcanzables);

		/* Se elimina estados identidades no finales */
		AFD afdPostIdentidades = new AFD();
		copiarAutomata(afdPostMinimizacion, afdPostIdentidades);
		eliminarIdentidades(afdPostIdentidades);

		return new AFDMin(afdOriginal, afdPostInalcanzables,
				afdPostMinimizacion, afdPostIdentidades);
	}

	/**
	 * Elimina los estados inalcanzables de un AFD.
	 */
	private static void eliminarInalcanzables(AFD afd) {            
		Conjunto<Estado> alcanzados = recuperarAlcanzados(afd);

		afd.getEstados().retener(alcanzados);
	}

	/**
         * Retorna los estados alcanzados
         * apartir del estado inicial de un AFD de entrada.
	 */
	private static Conjunto<Estado> recuperarAlcanzados(AFD afd) {
		Estado actual = afd.getEstadoInicial();
                
		Conjunto<Estado> alcanzados = new Conjunto<Estado>();
                
		alcanzados.agregar(actual);
                
		Stack<Estado> pila = new Stack<Estado>();
                
		pila.push(actual);

		while (!pila.isEmpty()) {
			actual = pila.pop();

			for (Transicion t : actual.getTransiciones()) {
				Estado e = t.getEstado();

				if (!alcanzados.contiene(e)) {
					alcanzados.agregar(e);
					pila.push(e);
				}
			}
		}

		return alcanzados;
	}

	/**
         * Algoritmo de minimizacion de estados.
	 */
	private static AFD minimizar(AFD afd) {
            
		Hashtable<Estado, Conjunto<Integer>> tabla1;
		Hashtable<Conjunto<Integer>, Conjunto<Estado>> tabla2;
		Conjunto<Conjunto<Estado>> particion = new Conjunto<Conjunto<Estado>>();

		particion.agregar(afd.getEstadosNoFinales());
		particion.agregar(afd.getEstadosFinales());
                
                
		Conjunto<Conjunto<Estado>> nuevaParticion;

		while (true) {
                    
			nuevaParticion = new Conjunto<Conjunto<Estado>>();

			for (Conjunto<Estado> grupo : particion) {
				if (grupo.cantidad() == 0) {
                                   
					continue;
				} else if (grupo.cantidad() == 1) {
                                    
					nuevaParticion.agregar(grupo);
				} else {
                                    
					tabla1 = new Hashtable<Estado, Conjunto<Integer>>();
					for (Estado e : grupo)
						tabla1.put(
								e,
								getGruposAlcanzados(e, particion,
										afd.getAlfabeto()));
                                        
					tabla2 = new Hashtable<Conjunto<Integer>, Conjunto<Estado>>();
					for (Estado e : grupo) {
						Conjunto<Integer> alcanzados = tabla1.get(e);
						if (tabla2.containsKey(alcanzados))
							tabla2.get(alcanzados).agregar(e);
						else {
							Conjunto<Estado> tmp = new Conjunto<Estado>();
							tmp.agregar(e);
							tabla2.put(alcanzados, tmp);
						}
					}
                                        
					for (Conjunto<Estado> c : tabla2.values())
						nuevaParticion.agregar(c);
				}
			}
                        
			nuevaParticion.ordenar();
                        
			if (nuevaParticion.equals(particion))
				break;
			else
				particion = nuevaParticion;
		}
                
		AFD afdPostMinimizacion = new AFD(afd.getAlfabeto(), afd.getExprReg());
                
		for (int i = 0; i < particion.cantidad(); i++) {
			Conjunto<Estado> grupo = particion.obtener(i);
			boolean esFinal = false;
                        
			if (tieneEstadoFinal(grupo))
				esFinal = true;
                        
			String etiqueta = obtenerEtiqueta(grupo);
                        
			Estado estado = new Estado(i, esFinal);
			estado.setEtiqueta(etiqueta);
			afdPostMinimizacion.agregarEstado(estado);
		}
                
		Hashtable<Estado, Estado> mapeo = new Hashtable<Estado, Estado>();
		for (int i = 0; i < particion.cantidad(); i++) {
                    
			Conjunto<Estado> grupo = particion.obtener(i);
                        
			Estado valor = afdPostMinimizacion.getEstado(i);
                        
			for (Estado clave : grupo)
				mapeo.put(clave, valor);
		}
                
		for (int i = 0; i < particion.cantidad(); i++) {
                    
			Estado representante = particion.obtener(i).obtenerPrimero();
                        
			Estado origen = afdPostMinimizacion.getEstado(i);
                        
			for (Transicion trans : representante.getTransiciones()) {
				Estado destino = mapeo.get(trans.getEstado());
				origen.getTransiciones().agregar(
						new Transicion(destino, trans.getSimbolo()));
			}
		}

		return afdPostMinimizacion;
	}

	/**
         * Partiendo de un estado
         * retorna los grupos en los que caen las tranciciones.
	 */
	private static Conjunto<Integer> getGruposAlcanzados(Estado origen,
			Conjunto<Conjunto<Estado>> particion, Alfabeto alfabeto) {
            
		Conjunto<Integer> gruposAlcanzados = new Conjunto<Integer>();
                
		HashMap<String, Estado> transiciones = origen
				.getTransicionesSegunAlfabeto(alfabeto);
                
		for (String s : alfabeto) {
                    
			Estado destino = transiciones.get(s);

			if (destino == null) {
                            
				gruposAlcanzados.agregar(-1);
			} else {
				for (int pos = 0; pos < particion.cantidad(); pos++) {
					Conjunto<Estado> grupo = particion.obtener(pos);

					if (grupo.contiene(destino)) {
						gruposAlcanzados.agregar(pos);
                                                
						break;
					}
				}
			}
		}

		return gruposAlcanzados;
	}

	/**
         * Elimina los estados identidad,
         * son aquellos estados que para todo simbolo de entrada
         * su transicion es el mismo.
         * No se eliminan si son estados de aceptacion.
	 */
	private static void eliminarIdentidades(AFD afd) {
            
		Conjunto<Estado> estadosEliminados = new Conjunto<Estado>();
                
		for (Estado e : afd.getEstados())
			if (e.getEsIdentidad() && !e.getEsFinal())
				estadosEliminados.agregar(e);

		if (estadosEliminados.estaVacio()) {
			
                    
			return;
		}
                
		for (Estado e : estadosEliminados)
			afd.getEstados().eliminar(e);
                
		Vector<List> transEliminadas = new Vector<List>();
                
		for (Estado e : afd.getEstados())
			for (Transicion t : e.getTransiciones())
				if (estadosEliminados.contiene(t.getEstado()))
					transEliminadas.add(Arrays.asList(t, e.getTransiciones()));
                
		for (List a : transEliminadas) {
			Transicion t = (Transicion) a.get(0);
			Conjunto<Transicion> c = (Conjunto<Transicion>) a.get(1);
			c.eliminar(t);
		}
                
	}

	/**
	 * Realiza una copia de un automata a otro.
	 */
	private static void copiarAutomata(Automata origen, Automata destino) {
		Automata.copiarEstados(origen, destino, 0);

                
		for (int i = 0; i < origen.cantidadEstados(); i++) {
			Estado tmp = origen.getEstado(i);

			destino.getEstado(i).setEsFinal(tmp.getEsFinal());
			destino.getEstado(i).setEtiqueta(tmp.getEtiqueta());
		}
                
		destino.setAlfabeto(origen.getAlfabeto());
		destino.setExprReg(origen.getExprReg());
	}

	/**
	 * Determina si un grupo de estados tiene un estado final.
	 */
	private static boolean tieneEstadoFinal(Conjunto<Estado> grupo) {
		for (Estado e : grupo)
			if (e.getEsFinal())
				return true;

		return false;
	}
        

	/**
	 * Calcula la nueva etiqueta para un estado del nuevo AFD, segun los estados
	 * agrupados.
	 */
	private static String obtenerEtiqueta(Conjunto<Estado> grupo) {
		String etiqueta = "";
		String pedazo;

		for (Estado e : grupo) {
                    
			if (e.toString().endsWith("if"))
				pedazo = e.toString().substring(0, e.toString().length() - 2);
			else if (e.toString().endsWith("i") || e.toString().endsWith("f"))
				pedazo = e.toString().substring(0, e.toString().length() - 1);
			else
				pedazo = e.toString();

                        
			etiqueta += pedazo + " ";
		}

		if (etiqueta.endsWith(" "))
			etiqueta = etiqueta.substring(0, etiqueta.length() - 1);

		return "(" + etiqueta + ")";
	}
        
}
