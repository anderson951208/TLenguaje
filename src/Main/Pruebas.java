
package Main;

import Estructuras.AFD;
import Estructuras.AFDMin;
import Estructuras.AFN;
import algoritmos.Minimizacion;
import algoritmos.Subconjuntos;
import analisis.Alfabeto;
import analisis.Analizador;

public class Pruebas {

    public static void main(String[] args) throws Exception {
        
        /*
        Alfabeto alfa = new Alfabeto("01");
        String er = "(0|10*1)*10*";
        Analizador as = new Analizador(alfa, er);
        */   
        
        
        /*
         *  CONVERSION REGEX -> AFN
         *  ALGORITMO DE THOMPSON
         */ 
        
        /*
        AFN salida = as.analizar();
        System.out.printf("AFN:\n%s", salida);
        */   
        
        
        /*
         *  CONVERSION AFN -> AFD
         *  ALGORITMO DE SUBCONJUNTOS
         */
        
        /*
        System.out.println();
        AFD afd = Subconjuntos.getAFD(salida);
        System.out.printf("AFD:\n%s", afd);
        System.out.printf("\nEstados AFD:\n%s", afd.estadosDtoString());
        */
        
       
       
        /*
         *  CONVERSION AFD -> AFD MINIMO
         *  ALGORITMO DE MINIMIZACION 
         */
       
        /*
        System.out.println();
        AFDMin afdMin = Minimizacion.getAFDminimo(afd);        
        System.out.printf("AFD Original:\n%s\n", afdMin.getAfdOriginal());
        System.out.printf("AFD Post Minimizacion:\n%s\n", afdMin.getAfdPostMinimizacion());
        System.out.printf("AFD Post Identidades (%s):\n%s\n", afdMin.identidadesEliminados() ? "<>" : "==", afdMin.getAfdPostIdentidades());
        
        System.out.println("estados finales son:\n" + afdMin.getAfdPostIdentidades().getEstadosFinales());
        */
        
       
    }
    
}
