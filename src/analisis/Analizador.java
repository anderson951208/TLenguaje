package analisis;

import algoritmos.Thompson;
import analisis.Alfabeto;
import analisis.AnalizadorLex;
import analisis.Token;
import analisis.TokenExprReg;
import Estructuras.AFN;

/**
 * Clase que hace la tradducion de una ER a AFN.
 * 
 */
public class Analizador {
      
    /**
     * El analizador lexico para este analizador
     */
    private AnalizadorLex analizadorLexico;
   
    /**
     * Variable para el token.
     */
    private Token preanalisis;
   
    /**
     * Contador de tokens recibidos.
     */
    private int contadorTokens;
   
    /**
     * Constructor
     */
    public Analizador(Alfabeto alfabeto, String exprReg) {
        analizadorLexico = new AnalizadorLex(alfabeto, exprReg);
        contadorTokens = 0;
    }

    /**
     * Este metodo retorna un AFN
     * aplicando thompson.
     */
    public AFN analizar() throws Exception {
        preanalisis = obtenerToken();
       
        if (preanalisis.getIdentificador() == TokenExprReg.FINAL)
            error("Expresion regular vacia");
       
        AFN afn = ExprReg();
        afn.setAlfabeto(analizadorLexico.getAlfabeto());
        afn.setExprReg(analizadorLexico.getExpresionRegular());
       
        if (preanalisis.getIdentificador() != TokenExprReg.FINAL)
            error("Caracter invalido");
       
        return afn;
    }
   
    /**
     * Metodo que procesa las uniones de la expresion regular.
     * 
     */
    private AFN ExprReg() throws Exception {
        
        AFN afn1 = Concat();
        AFN afn2 = R1();
       
        if (afn2 == null)
            return afn1;
        else
            return Thompson.union(afn1, afn2);
    }
   
    /**
     * Metodo que procesa las uniones en forma de lista.
     * 
     */
    private AFN R1() throws Exception {
        if (preanalisis.getIdentificador() == TokenExprReg.UNION) {
            match(preanalisis);
            
            AFN afn1 = Concat();
            AFN afn2 = R1();

            if (afn2 == null)
                return afn1;
            else
                return Thompson.union(afn1, afn2);
        }
        else {
            
            return null;
        }
    }

    /**
     * Metodo que procesa una concatenacion en la expresion regular.
     */
    private AFN Concat() throws Exception {
               
        AFN afn1 = Grupo();
        AFN afn2 = R2();
       
        if (afn2 == null)
            return afn1;
        else
            return Thompson.concatenacion(afn1, afn2);
    }
   
    /**
     * Metodo que procesa una concatenacion en forma de lista.
     */
    private AFN R2() throws Exception {
        switch (preanalisis.getIdentificador()) {
            case PAREN_IZQUIERDO:
            case ALFABETO:
                
                AFN afn1 = Grupo();
                AFN afn2 = R2();

                if (afn2 == null)
                    return afn1;
                else
                    return Thompson.concatenacion(afn1, afn2);
            default:
                
                return null;
        }
    }
   
    /**
     * Metodo que aplica operador.
     */
    private AFN Grupo() throws Exception {
               
        AFN afn = Elem();
        TokenExprReg operador = Oper();
       
        switch (operador) {
            case CERRADURA_KLEENE:
                return Thompson.cerraduraKleene(afn);
            case CERRADURA_POSITIVA:
                return Thompson.cerraduraPositiva(afn);
            default:
                return afn;
        }
    }
   
    /**
     * Metodo que procesa un operador en la ER.
     */
    private TokenExprReg Oper() throws Exception {
        TokenExprReg operador;
       
        switch (preanalisis.getIdentificador()) {
            case CERRADURA_KLEENE:
            case CERRADURA_POSITIVA:
                operador = preanalisis.getIdentificador();
               
                
                match(preanalisis);
                break;
            default:
                // Derivar en vacio
                
                operador = TokenExprReg.DESCONOCIDO;
        }
       
        return operador;
    }
 
    /**
     * Este metodo comprueba si un parentesis de apertura
     * tiene su respectivo parantesis final.
     */
    private AFN Elem() throws Exception {
        AFN afn = null;
       
        switch (preanalisis.getIdentificador()) {
            case PAREN_IZQUIERDO:
                               
                match(new Token(TokenExprReg.PAREN_IZQUIERDO));
                afn = ExprReg();
                match(new Token(TokenExprReg.PAREN_DERECHO));
                break;
            case ALFABETO:
                               
                afn = SimLen();
                break;
            default:
                error("Se espera parentesis de apertura o simbolo de alfabeto. ");
        }
       
        return afn;
    }
   
    /**
     * Metodo que procesa un simbolo del alfabeto en la expresion regular.
     */
    private AFN SimLen() throws Exception {
        String simbolo = preanalisis.getValor();
       
        if (!analizadorLexico.getAlfabeto().contiene(simbolo)) {
            error("El simbolo \"" + simbolo +
                "\" no pertenece al alfabeto definido.");
        }
       
        AFN afn = Thompson.basico(simbolo);
        match(preanalisis);
        return afn;
    }

    /**
     * Metodo que se encarga de comprobar que la
     * entrada es la correcta para pasar al siguiente
     * token.
     */
    private void match(Token entrada) throws Exception {
        if (preanalisis.equals(entrada))
            preanalisis = obtenerToken();
        else if (entrada.getIdentificador() == TokenExprReg.PAREN_DERECHO)
            error("Falta parentesis de cierre");
        else
            error("Caracter invalido");
    }
   
    /**
     * Metodo que se encarga de lanzar excepciones
     * para los distintos casos de error.
     */
    private void error(String mensaje) throws Exception {
        String mensajeCompleto = "";
       
        mensajeCompleto += "Error de sintaxis\n";
        mensajeCompleto += "Caracter: " + preanalisis.getValor() + "\n";
        mensajeCompleto += "Mensaje : " + mensaje;
       
        throw new Exception(mensajeCompleto);
    }
   
    /**
     * Metodo que obtiene el siguiente token y registra
     * la cantidad de tokens leidos.
     */
    private Token obtenerToken() throws Exception {
        ++contadorTokens;
        return analizadorLexico.sgteToken();
    }
    
}