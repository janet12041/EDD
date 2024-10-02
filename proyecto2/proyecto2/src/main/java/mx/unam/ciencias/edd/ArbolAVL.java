package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            int alturaI =
		hayIzquierdo() ? ((VerticeAVL)izquierdo).altura : -1;
	    int alturaD = hayDerecho() ? ((VerticeAVL)derecho).altura : -1;
            return super.toString() + " " + altura + "/" + 
		(alturaI - alturaD);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
	VerticeAVL vertice = avl(ultimoAgregado.padre);
	rebalancea(vertice);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeArbolBinario<T> v = busca(elemento);
        VerticeAVL vertice = avl(v);
	if(vertice == null)
	    return;
	if(vertice.hayIzquierdo() && vertice.hayDerecho())
	    vertice = avl(intercambiaEliminable(vertice));
	VerticeAVL padre = avl(vertice.padre);
	eliminaVertice(vertice);
	elementos--;
	rebalancea(padre);
    }

    private VerticeAVL avl(VerticeArbolBinario<T> vertice) {
	if(vertice == null)
	    return null;
	return (VerticeAVL)vertice;
    }

    private void rebalancea(VerticeAVL vertice) {
	if(vertice == null)
	    return;
	int alturaI =
	    vertice.hayIzquierdo() ? avl(vertice.izquierdo).altura : -1;
	int alturaD =
	    vertice.hayDerecho() ? avl(vertice.derecho).altura : -1;
	vertice.altura = (alturaI > alturaD) ? alturaI+1 : alturaD+1;
	int balanceV = alturaI - alturaD;

	int h = vertice.altura;
	VerticeAVL hi = avl(vertice.izquierdo);
	VerticeAVL hd = avl(vertice.derecho);
	
	if(balanceV == -2) {

	    VerticeAVL hdi = avl(hd.izquierdo);
	    VerticeAVL hdd = avl(hd.derecho);
	    
	    if(balance(hd) == 1) {
		super.giraDerecha(hd);

		hdi.altura = h-1;
		hd.altura = h-2;

		hd = avl(vertice.derecho);
		hdi = avl(hd.izquierdo);
		hdd = avl(hd.derecho);
	    }
	    
	    hdd.altura = h-2;
	    super.giraIzquierda(vertice);

	    int hdiAltura = hdi != null ? hdi.altura() : -1;
	    
	    if(hdiAltura == h-2)
		vertice.altura = h-1;
	    else if(hdiAltura == h-3 || hdiAltura == h-4)
		vertice.altura = h-2;

	    if(vertice.altura == h-1)
		hd.altura = h;
	    else if(vertice.altura == h-2)
		hd.altura = h-1;

	} else if(balanceV == 2) {

	    VerticeAVL hii = avl(hi.izquierdo);
	    VerticeAVL hid = avl(hi.derecho);

	    if(balance(hi) == -1) {

		super.giraIzquierda(hi);
		hid.altura = h-1;
		hi.altura = h-2;

		hi = avl(vertice.izquierdo);
		hii = avl(hi.izquierdo);
		hid = avl(hi.derecho);
		
	    }

	    hii.altura = h-2;
	    super.giraDerecha(vertice);

	    int hidAltura = hid != null ? hid.altura() : -1;
	
	    if(hidAltura == h-2)
		vertice.altura = h-1;
	    else if(hidAltura == h-3 || hidAltura == h-4)
		vertice.altura = h-2;

	    if(vertice.altura == h-1)
		hi.altura = h;
	    else if(vertice.altura == h-2)
		hi.altura = h-1;
	}
        
	rebalancea(avl(vertice.padre));
	
    }

    private int balance(VerticeAVL vertice) {
        int alturaI =
	    vertice.hayIzquierdo() ? avl(vertice.izquierdo).altura : -1;
	int alturaD =
	    vertice.hayDerecho() ? avl(vertice.derecho).altura : -1;
        return (alturaI - alturaD); 
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
