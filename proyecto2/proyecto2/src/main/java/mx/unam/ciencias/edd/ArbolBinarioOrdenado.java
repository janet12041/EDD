package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<Vertice>();
	    Vertice v = raiz;
	    while(v != null) {
		pila.mete(v);
		v = v.izquierdo;
	    }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice vertice = pila.saca();
	    if(vertice.hayDerecho()) {
		Vertice v = vertice.derecho;
	        while(v != null) {
		    pila.mete(v);
		    v = v.izquierdo;
		}
	    }
	    return vertice.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    private void auxiliar(Vertice actual, Vertice nuevo) {
	if(nuevo.elemento.compareTo(actual.elemento) <= 0) {
	    if(actual.izquierdo == null) {
		actual.izquierdo = nuevo;
		nuevo.padre = actual;
	    } else
		auxiliar(actual.izquierdo, nuevo);
	} else {
	    if(actual.derecho == null) {
		actual.derecho = nuevo;
		nuevo.padre = actual;
	    } else
		auxiliar(actual.derecho, nuevo);
	}
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
           throw new IllegalArgumentException();
        Vertice vertice = nuevoVertice(elemento);
	elementos++;
	if(raiz == null)
	    raiz = vertice;
	else
	    auxiliar(raiz, vertice);
	ultimoAgregado = vertice;
    }

    /**
     * Ecuentra el vertice con el elemento máximo en un subarbol no vacio.
     * @param raiz el vertice raiz del subarbol.
     * @retur el vertice con el elemento máximo. 
     */
    private Vertice maximoEnSubarbol(Vertice raiz) {
	if(!raiz.hayDerecho())
	    return raiz;
	return maximoEnSubarbol(raiz.derecho);
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));
	if(vertice == null)
	    return;
	elementos--;
        if(vertice.hayIzquierdo() && vertice.hayDerecho())
	    vertice = intercambiaEliminable(vertice);
	eliminaVertice(vertice);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice v = maximoEnSubarbol(vertice.izquierdo);
        T elemento = vertice.elemento;
	vertice.elemento = v.elemento;
	v.elemento = elemento;
	return v;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice u =
	    vertice.hayIzquierdo() ? vertice.izquierdo : vertice.derecho;   
	    if(vertice.hayPadre()) {
		if(vertice == vertice.padre.izquierdo) {
		    vertice.padre.izquierdo = u;
		} else {
		    vertice.padre.derecho = u;
		    }
	 } else
	    raiz = u;
	 if(u != null)
	    u.padre = vertice.padre;
    }

    private VerticeArbolBinario<T>
	busca(Vertice vertice, T elemento) {
	if(vertice == null)
	    return null;
	if(elemento.compareTo(vertice.elemento) == 0)
	    return vertice;
	else if(elemento.compareTo(vertice.elemento) < 0)
	    return busca(vertice.izquierdo, elemento);
	else
	    return busca(vertice.derecho, elemento);
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return busca(raiz, elemento);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice);
        if(!v.hayIzquierdo())
	    return;
	Vertice i = v.izquierdo;
	if(v.hayPadre()) {
	    if(v == v.padre.izquierdo)
		v.padre.izquierdo = i;
	    else
		v.padre.derecho = i;
	} else {
	    raiz = i;
	}
	i.padre = v.padre;
	v.padre = i;
	v.izquierdo = i.derecho;
	if(i.hayDerecho()) 
	   i.derecho.padre = v;
	i.derecho = v;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice);
        if(!v.hayDerecho())
	    return;
	Vertice d = v.derecho;
	if(v.hayPadre()) {
	    if(v == v.padre.izquierdo)
		v.padre.izquierdo = d;
	    else
		v.padre.derecho = d;
	} else {
	    raiz = d;
	}
	d.padre = v.padre;
	v.padre = d;
	v.derecho = d.izquierdo;
	if(d.hayIzquierdo()) 
	   d.izquierdo.padre = v;
	d.izquierdo = v;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en el elemento del vértice dado y ejectuta el método
     * para sus vértice izquierdo y derecho.
     * @param v el vertice al que se le hará la acción.
     * @param accion la acción a realizar al elemento del vértice.
     */
    private void dfsPreOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
	if(v == null)
	    return;
	accion.actua(v);
	dfsPreOrder(v.izquierdo, accion);
	dfsPreOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
       dfsPreOrder(raiz, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en el elemento del vértice dado y ejectuta el método
     * para sus vértice izquierdo y derecho.
     * @param v el vertice al que se le hará la acción.
     * @param accion la acción a realizar al elemento del vértice.
     */
    private void 
    dfsInOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if(v == null)
	    return;
	dfsInOrder(v.izquierdo, accion);
	accion.actua(v);
	dfsInOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder(raiz, accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en el elemento del vértice dado y ejectuta el método
     * para sus vértice izquierdo y derecho.
     * @param v el vertice al que se le hará la acción.
     * @param accion la acción a realizar al elemento del vértice.
     */
    private void dfsPostOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if(v == null)
	    return;
	dfsPostOrder(v.izquierdo, accion);
	dfsPostOrder(v.derecho, accion);
	accion.actua(v);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder(raiz, accion);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
