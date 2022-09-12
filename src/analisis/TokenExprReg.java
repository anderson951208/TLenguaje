package analisis;



public enum TokenExprReg {
    /**
     * Parentesis derecho, ")".
     */
    PAREN_DERECHO,
    
    /**
     * Parentesis izquierdo, "(".
     */
    PAREN_IZQUIERDO,
    
    /**
     * Operador de union, "|".
     */
    UNION,
    
    /**
     * Operador de cerradura de Kleene, "*".
     */
    CERRADURA_KLEENE,
    
    /**
     * Operador de cerradura positiva, "+".
     */
    CERRADURA_POSITIVA,
       
    /**
     * Operador de concatenacion (#).
     */
    CONCATENACION,
    
    /**
     * Un simbolo del alfabeto.
     */
    ALFABETO,
    
    /**
     * Finalizador de una expresion regular (EOF).
     */
    FINAL,
    
    /**
     * Token desconocido (invalido).
     */
    DESCONOCIDO
}
