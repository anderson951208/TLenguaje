package analisis;

import java.util.Hashtable;



/**
 * Esta clase valida Si esta bien escrita la ER y verifica los tokens validos
 */
public class AnalizadorLex {
   
    /**
     * Alfabeto
     */
    private Alfabeto alfabeto;
   
    /**
     * Exprecion regular
     */
    private String exprReg;
   
    /**
     * buffer de entrada. para guardar la ER para utulizar en otros metodos.
     */
    private StringBuffer buffer;
   
    /**
     * Tabla de simbolos validos esperados por el analizador lexico.
     */
    private Hashtable<String, TokenExprReg> tablaSimbolos;
   
    /**
     * Constructor de la clase.
     */
    public AnalizadorLex(Alfabeto alfabeto, String exprReg) {
       this.alfabeto = alfabeto;
       this.exprReg  = exprReg;
       this.buffer   = new StringBuffer(exprReg);
       crearTablaSimbolos();
    }
   
    /**
     * Este metodo se encarga de consumir caracteres del buffer
     * de entrada, convertirlos a tokens y retornarlos al Analizador
     * Sintactico.
     */
    public Token sgteToken() throws Exception {
        String lexema = sgteLetra();

        // Omitimos cualquier tipo de espacio en blanco
        if (lexema.matches("\\s"))
            return sgteToken();

        TokenExprReg tipoToken = tablaSimbolos.get(lexema);

        if (tipoToken == null)
            return new Token(TokenExprReg.DESCONOCIDO, lexema);
        else if (tipoToken == TokenExprReg.ALFABETO)
            return new Token(TokenExprReg.ALFABETO, lexema);
        else
            return new Token(tipoToken);
    }
   
    /**
     * Obtiene el Alfabeto
     */
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }
   
    /**
     * Obtiene la ER
     */
    public String getExpresionRegular() {
        return exprReg;
    }
   
    /**
     * 
     * Este metodo consume cada uno de los caracteres del buffer
     */
    private String sgteLetra() {
        String salida = "";
       
        if (buffer.length() > 0) {
            salida += buffer.charAt(0);
            buffer.deleteCharAt(0);
        }
       
        return salida;
    }
   
    private void crearTablaSimbolos() {
        tablaSimbolos = new Hashtable<String, TokenExprReg>();

        tablaSimbolos.put("*", TokenExprReg.CERRADURA_KLEENE);
        tablaSimbolos.put("+", TokenExprReg.CERRADURA_POSITIVA);
        tablaSimbolos.put("|", TokenExprReg.UNION);
        tablaSimbolos.put("(", TokenExprReg.PAREN_IZQUIERDO);
        tablaSimbolos.put(")", TokenExprReg.PAREN_DERECHO);
        tablaSimbolos.put("", TokenExprReg.FINAL);

        for (int i=0; i < alfabeto.getCantidad(); i++) {
            String simbolo = alfabeto.getSimbolo(i);
            tablaSimbolos.put(simbolo, TokenExprReg.ALFABETO);
        }
    }
}


