package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
	    color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            String elemento = super.toString();
            return color == Color.ROJO ? "R{"+elemento+"}":"N{"+elemento+"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return ((VerticeRojinegro)vertice).color;
    }

    private boolean esRojo(VerticeRojinegro vertice) {
	return (vertice != null && vertice.color == Color.ROJO);
    }

    private void rebalanceaAgrega(VerticeRojinegro vertice) {
	// Caso 1.
	if(vertice.padre == null) {
	    vertice.color = Color.NEGRO;
	    return;
	}

	// Caso 2.
	VerticeRojinegro padre = (VerticeRojinegro)(vertice.padre);
	if(padre.color == Color.NEGRO)
	    return;

	// Caso 3.
	VerticeRojinegro abuelo =(VerticeRojinegro)padre.padre;
	boolean pIzquierdo = (padre == abuelo.izquierdo);
	Vertice t = pIzquierdo ? abuelo.derecho : abuelo.izquierdo;
	VerticeRojinegro tio = t == null ? null : (VerticeRojinegro)t;
	if(esRojo(tio)){
	    padre.color = Color.NEGRO;
	    tio.color = Color.NEGRO;
	    abuelo.color = Color.ROJO;
	    rebalanceaAgrega(abuelo);
	    return;
	}
	
	// Caso 4.
	boolean vIzquierdo = vertice == padre.izquierdo;
        if(pIzquierdo && !vIzquierdo) {
	    super.giraIzquierda(padre);
	    vertice = padre;
        } else if(!pIzquierdo && vIzquierdo) {
  	    super.giraDerecha(padre);
  	    vertice = padre;
  	}
  	padre = (VerticeRojinegro)vertice.padre;
	vIzquierdo = vertice == padre.izquierdo;

	// Caso 5.
	padre.color = Color.NEGRO;
	abuelo.color = Color.ROJO;
	if(vIzquierdo)
	    super.giraDerecha(abuelo);
	else
	    super.giraIzquierda(abuelo);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
	VerticeRojinegro vertice = (VerticeRojinegro)ultimoAgregado;
	vertice.color = Color.ROJO;
	rebalanceaAgrega(vertice);
    }

    private void rebalanceaElimina(VerticeRojinegro vertice) {
	// Caso 1.
	if(vertice.padre == null)
	    return;

	// Caso 2.
	VerticeRojinegro padre = (VerticeRojinegro)vertice.padre;
	boolean vIzquierdo = vertice == padre.izquierdo;
	VerticeRojinegro hermano =
	  (VerticeRojinegro)(vIzquierdo ? padre.derecho : padre.izquierdo);
	if(esRojo(hermano)) {
	    padre.color = Color.ROJO;
	    hermano.color = Color.NEGRO;
	    if(vIzquierdo)
		super.giraIzquierda(padre);
	    else
		super.giraDerecha(padre);
	    hermano =
	  (VerticeRojinegro)(vIzquierdo ? padre.derecho : padre.izquierdo);
	}

	// Caso 3.
	VerticeRojinegro hi = 
	    hermano.izquierdo == null ? null : (VerticeRojinegro)hermano.izquierdo;
	VerticeRojinegro hd = 
	    hermano.derecho == null ? null : (VerticeRojinegro)hermano.derecho;
	if(!esRojo(padre) && !esRojo(hermano) && !esRojo(hi) && !esRojo(hd)) {
	    hermano.color = Color.ROJO;
	    rebalanceaElimina(padre);
	    return;
	}

	// Caso 4.
	if(!esRojo(hermano) && !esRojo(hi) && !esRojo(hd) && esRojo(padre)) {
	    hermano.color = Color.ROJO;
	    padre.color = Color.NEGRO;
	    return;
        }

	// Caso 5.
	if((vIzquierdo && esRojo(hi) && !esRojo(hd)) ||
	   (!vIzquierdo && !esRojo(hi) && esRojo(hd))) {
	    hermano.color = Color.ROJO;
	    if(esRojo(hi))
		hi.color = Color.NEGRO;
	    else
		hd.color = Color.NEGRO;
	    if(vIzquierdo)
		super.giraDerecha(hermano);
	    else
		super.giraIzquierda(hermano);
	    hermano =
	  (VerticeRojinegro)(vIzquierdo ? padre.derecho : padre.izquierdo);
	    hi = 
	     hermano.izquierdo == null ? null: (VerticeRojinegro)hermano.izquierdo;
	    hd = 
	     hermano.derecho == null ? null :(VerticeRojinegro)hermano.derecho;
	}

	// Caso 6.
	hermano.color = padre.color;
        padre.color = Color.NEGRO;
	if(vIzquierdo)
            hd.color = Color.NEGRO;
	else
	    hi.color = Color.NEGRO;
	if(vIzquierdo)
	    super.giraIzquierda(padre);
	else
	    super.giraDerecha(padre);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeArbolBinario<T> v = busca(elemento);
        VerticeRojinegro vertice = 
            v == null ? null : (VerticeRojinegro)v;
	if(vertice == null)
	    return;
	if(vertice.hayIzquierdo() && vertice.hayDerecho())
	    vertice = (VerticeRojinegro)intercambiaEliminable(vertice);
	if(!vertice.hayIzquierdo() && !vertice.hayDerecho()) {
	    vertice.izquierdo = nuevoVertice(null);
	    vertice.izquierdo.padre = vertice;
	}
	VerticeRojinegro hijo =
            (VerticeRojinegro)(vertice.hayIzquierdo() ? vertice.izquierdo : vertice.derecho);
	eliminaVertice(vertice);
	elementos--;
	if(esRojo(hijo)) {
	    hijo.color = Color.NEGRO;
	    return;
	} else if(!esRojo(vertice) && !esRojo(hijo))
	    rebalanceaElimina(hijo);
	if(hijo.elemento == null)
	    eliminaVertice(hijo);
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
