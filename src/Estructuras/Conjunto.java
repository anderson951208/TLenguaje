package Estructuras;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Esta clase representa conjuntos de estados y trnaciiones
 * que posen los AF
 */

public class Conjunto<T extends Comparable<T>>
    implements Iterable<T>, Comparable<Conjunto<T>> {
   
    /**
     * Conjunto de elementos.
     */
    private Vector<T> elementos;
   
    /**
     * Indica si el conjunto esta ordenado.
     */
    private boolean estaOrdenado;
   
    /**
     * Constructor de la clase.
     */
    public Conjunto() {
        elementos = new Vector<T>();
       
        estaOrdenado = true;
    }
   
    /**
     * Agrega un nuevo elemento al conjunto.
     */
    public void agregar(T elemento) {
        
        if (!estaVacio() && obtenerUltimo().compareTo(elemento) > 0)
            estaOrdenado = false;
       
        elementos.add(elemento);
    }
   
    /**
     * Elimina un elemento del conjunto.
     */
    public void eliminar(T elemento) {
        boolean eliminado = elementos.remove(elemento);
       
        if (eliminado && !getEstaOrdenado())
            estaOrdenado = verificarOrden();
    }
   
    /**
     * Retorna el i-esimo elemento del conjunto.
     */
    public T obtener(int i) {
        return elementos.get(i);
    }
   
    /**
     * Retorna la posicion de la primera ocurrencia
     * de un elemento en el Conjunto
     */
    public int obtenerPosicion(T elemento) {
        return elementos.indexOf(elemento);
    }
   
    /**
     * Retorna el primer elemento del conjunto.
     */
    public T obtenerPrimero() {
        return elementos.firstElement();
    }
   
    /**
     * Retorna el último elemento del conjunto.
     */
    public T obtenerUltimo() {
        return elementos.lastElement();
    }    
    /**
     * Retorna la cantidad de transiciones del conjunto.
     */
    public int cantidad() {
        return elementos.size();
    }
   
    /**
     * verifica si el Conjunto esta vacio
     */
    public boolean estaVacio() {
        return cantidad() == 0;
    }

    /**
     * Retorna un iterador sobre los elementos del conjunto.
     * para recorrer los elementos del conjunto(estados o transiciones).
     */
    public Iterator<T> iterator() {
        return elementos.iterator();
    }
   
    /**
     * Comprueba si un elemento esta en el Conjunto.
     */
    public boolean contiene(T elemento) {
        return elementos.contains(elemento);
    }
   
    /**
     * Retiene en este Conjunto solo aquellos elementos
     * que esten contenidos en un subconjunto.
     */
    public boolean retener(Conjunto<T> subconjunto) {
        return elementos.retainAll(subconjunto.elementos);
    }
   
    /**
     * Ordena los elementos del conjunto.
     */
    public void ordenar() {
        Object[] arregloTemp = elementos.toArray();
        Arrays.sort(arregloTemp);
       
        elementos.clear();
        for (Object e : arregloTemp)
            elementos.add((T) e);
    }
   
    /**
     * Verifica si el conjunto esta ordenado o no.
     */
    public boolean getEstaOrdenado() {
        return estaOrdenado;
    }
   
    /**
     * Verifica si el conjunto esta ordenado, realizando
     * una comparacion entre pares de elementos.
     */
    private boolean verificarOrden() {
        if (estaVacio())
            return true;
       
        T actual = obtenerPrimero();
       
        for (int i=1; i < cantidad(); i++) {
            T sgte = obtener(i);
           
            if (actual.compareTo(sgte) > 0)
                return false;
           
            actual = sgte;
        }
       
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
       
        if (getClass() != obj.getClass())
            return false;
       
        final Conjunto<T> other = (Conjunto<T>) obj;
        return this.elementos.equals(other.elementos);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.elementos != null ? this.elementos.hashCode() : 0);
        return hash;
    }
   
    @Override
    public String toString() {
        return elementos.toString();
    }

    public int compareTo(Conjunto<T> obj) {
        if (!getEstaOrdenado())
            ordenar();
       
        if (!obj.getEstaOrdenado())
            obj.ordenar();
       
        for (int i=0; /* sin condicion */; i++) {
            /* Si ambos Conjuntos tienen mas elementos */
            if (cantidad() > i && obj.cantidad() > i) {
                T a = obtener(i);
                T b = obj.obtener(i);
                int cmp = a.compareTo(b);
               
                /* Si hay diferencias, basta para comparar */
                if (cmp != 0)
                    return cmp;
            }
            else if (cantidad() > i) /* Si este Conjunto tiene mas elementos, sera el mayor */
                return 1;
            else if (obj.cantidad() > i) /* Si este Conjunto tiene menos elementos, sera el menor */
                return -1;
            else /* Si ninguno tiene mas elementos, son iguales */
                return 0;
        }
    }
}
