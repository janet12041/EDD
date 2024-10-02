package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @param n el nodo del que se regresara una representación en cadena.
     * @return una representación en cadena de la cola.
     */
    private String toString(Nodo n) {
	if(n.siguiente != null) 
	    return String.format("%s,", n.elemento) + toString(n.siguiente);
        return String.format("%s,", n.elemento);
     }

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        if (cabeza == null)
	    return "";
        return toString(cabeza);
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null)
	    throw new IllegalArgumentException();
	Nodo n = new Nodo(elemento);
	if (cabeza == null) {
	    cabeza = rabo = n;
	} else {	    
            rabo.siguiente = n;
	    rabo = n;
	}
    }
}
