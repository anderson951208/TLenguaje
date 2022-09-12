/**
 * 
 */
package analisis;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

/**
 * Clase que representa el alfabeto sobre el cual se construye la ER.
 */
public class Alfabeto implements Iterable<String> {
   
    /**
     * letra que representa el simbolo vacio.
     */
    public static final String VACIO = "?";
   
    /**
     * Conjunto de simbolos del alfabeto.
     */
    private Vector<String> simbolos;
   
    /**
     * Contructor de la clase.
     */
    public Alfabeto(String caracteres) {      
        String[] arregloTemp = new String[caracteres.length()];
        for (int i=0; i < caracteres.length(); i++)
            arregloTemp[i] = "" + caracteres.charAt(i);
       
        Arrays.sort(arregloTemp);
       
        simbolos = new Vector<String>(arregloTemp.length);
        for (int i=0; i < arregloTemp.length; i++) {
            String temp = arregloTemp[i];
            if (!simbolos.contains(temp))
                simbolos.add(temp);
        }  
    }
   
    /**
     * Retorna el tamaño de este alfabeto.
     */
    public int getCantidad() {
        return simbolos.size();
    }
   
    /**
     * Retorna un simbolo del alfabeto.
     */
    public String getSimbolo(int pos) {
        if (pos == getCantidad())
            return Alfabeto.VACIO;
        else
            return simbolos.get(pos);
    }
   
    /**
     * permite conocer si un caracter pertenece al alfabeto.
     */
    public boolean contiene(String caracter) {
        return simbolos.contains(caracter);
    }
   
    /**
     * Retorna la posicion de la primera ocurrencia
     * de un caracter del alfabeto.
     */
    public int obtenerPosicion(String caracter) {
        if (caracter.equals(Alfabeto.VACIO))
            return getCantidad();
        else
            return simbolos.indexOf(caracter);
    }

    public Iterator<String> iterator() {
        return simbolos.iterator();
    }
}