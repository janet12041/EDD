package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<Vertice>();
	    if(raiz != null)
		cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice vertice = cola.saca();
	    if(vertice.izquierdo != null)
		cola.mete(vertice.izquierdo);
	    if(vertice.derecho != null)
	        cola.mete(vertice.derecho);
	    return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
	    throw new IllegalArgumentException();
        Vertice vertice = nuevoVertice(elemento);
        elementos++;
	if(raiz == null)
	    raiz = vertice;
	else{
            Cola<Vertice> cola = new Cola<Vertice>();
   	    cola.mete(raiz);
   	    boolean noAgregado = true;
   	    while(noAgregado) {
                Vertice v = cola.saca();
   	        if(!v.hayIzquierdo()) {
		    v.izquierdo = vertice;
		    vertice.padre = v;
	            noAgregado = false;
                } else if(!v.hayDerecho()) {
	            v.derecho = vertice;
		    vertice.padre = v;
		    noAgregado = false;
		} else {
   	            if(v.hayIzquierdo()) 
   	                cola.mete(v.izquierdo);
   	            if(v.hayDerecho())
   	                cola.mete(v.derecho);
   	        }
   	    }
   	}
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));
	if(vertice == null)
	    return;
	elementos--;
	if (elementos == 0) {
	    raiz = null;
	    return;
	}
        Cola<Vertice> cola = new Cola<Vertice>();
   	cola.mete(raiz);
   	Vertice v = null;
   	while(!cola.esVacia()) {
            v = cola.saca();
   	    if(v.hayIzquierdo()) 
   	        cola.mete(v.izquierdo);
   	    if(v.hayDerecho())
   	        cola.mete(v.derecho);
   	}
	Vertice ultimo = v;
	
        if(ultimo == ultimo.padre.izquierdo)
	    ultimo.padre.izquierdo = null;
	else 
	    ultimo.padre.derecho = null;
	vertice.elemento = ultimo.elemento;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if(esVacia())
           return -1;
        return (int)(Math.floor(Math.log(elementos)/Math.log(2)));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if(raiz == null)
            return;
        Cola<Vertice> cola = new Cola<Vertice>();
   	cola.mete(raiz);
   	while(!cola.esVacia()) {
            Vertice v = cola.saca();
   	    accion.actua(v);
   	    if(v.hayIzquierdo()) 
   	        cola.mete(v.izquierdo);
   	    if(v.hayDerecho())
   	        cola.mete(v.derecho);
   	}
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
