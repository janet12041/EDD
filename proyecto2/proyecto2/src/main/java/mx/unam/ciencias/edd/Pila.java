package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @param n el nodo del que se regresara una representación en cadena.
     * @return una representación en cadena de la pila.
     */
    private String toString(Nodo n) {
	if(n.siguiente != null) 
	    return String.format("%s\n", n.elemento) + toString(n.siguiente);
        return String.format("%s\n", n.elemento);
     }

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        if (cabeza == null)
	    return "";
        return toString(cabeza);
    }

    /**
     * Agrega un elemento al tope de la pila.
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
	    n.siguiente = cabeza;
	    cabeza = n;
	}
    }
}
