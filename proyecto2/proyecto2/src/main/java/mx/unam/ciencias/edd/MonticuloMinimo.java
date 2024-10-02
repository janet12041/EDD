package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            if(indice >= elementos)
		throw new NoSuchElementException();
	    indice++;
	    return arbol[indice - 1];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            this.elemento = elemento;
	    indice = -1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
	this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        arbol = nuevoArreglo(n);
	
	int c = 0;
	for(T elem : iterable) {
	    arbol[c] = elem;
	    elem.setIndice(c);
	    c++;
	}
	elementos = n;
	
	for(int i = n/2; i >= 0; i--)
	    acomodaHaciaAbajo(i);

	for(int i = 0; i<elementos; i++){
	    int izq = 2*i + 1;
	    int der = 2*i + 2;
	    if(izq < elementos && arbol[i].compareTo(arbol[izq]) > 0)
		System.out.println(arbol[izq]);
	    if(der < elementos && arbol[i].compareTo(arbol[der]) > 0)
		System.out.println(arbol[der]);
	}
    }

    private int padre(int p) {
	if(p < 0 || p >= elementos)
	    return -1;
	return (int)Math.floor((p-1)/2);
    }

    private int hijoMenor(int v) {
	if(v < 0 || v >= elementos)
	    return elementos;
	int i = (2*v)+1;
	int d = (2*v)+2;
	
	if(i >= elementos)
	    return elementos;
	if(d >= elementos)
	    return i;
	return (arbol[i].compareTo(arbol[d]) > 0)? d : i; 
    }

    private void intercambia(int i1, int i2){
	T e1 = arbol[i1];
	T e2 = arbol[i2];
	arbol[i1] = e2;
	arbol[i2] = e1;
	e1.setIndice(i2);
	e2.setIndice(i1);
    }

    private void acomodaHaciaArriba(int iV) {
        int iP = padre(iV);
	if(iP < 0)
	    return;
        if(arbol[iV].compareTo(arbol[iP]) < 0){
	    intercambia(iV, iP);
	    acomodaHaciaArriba(iP);
	}
    }

    private void acomodaHaciaAbajo(int iV){
	int iHM = hijoMenor(iV);
	if(iHM >= elementos)
	    return;
        if(arbol[iV].compareTo(arbol[iHM]) > 0){
	    intercambia(iV, iHM);
	    acomodaHaciaAbajo(iHM);
	}
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        if(elementos == arbol.length) {
	    T[] nuevo = nuevoArreglo(2*elementos);
	    for(int i = 0; i < elementos; i++)
		nuevo[i] = arbol[i];
	    arbol = nuevo;
	}
	arbol[elementos] = elemento;
	elemento.setIndice(elementos);
	elementos++;
	acomodaHaciaArriba(elementos-1);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
	if(esVacia())
	    throw new IllegalStateException();
        T raiz = arbol[0];
	intercambia(0, elementos-1);
	/*T ultimo = arbol[elementos-1];
	arbol[0] = ultimo;
	ultimo.setIndice(0);
	arbol[elementos-1] = raiz;
	raiz.setIndice(-1);*/
	raiz.setIndice(-1);
	elementos--;

        acomodaHaciaAbajo(0);
	return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
	if(!contiene(elemento))
	    return;
	
	int i = elemento.getIndice();
	intercambia(i, elementos-1);

	/*T ultimo = arbol[elementos-1];
        arbol[i] = ultimo;
	ultimo.setIndice(i);
	arbol[elementos-1] = elemento;*/
	elemento.setIndice(-1);
	elementos--;

	reordena(arbol[i]);
        /*if(ultimo.compareTo(elemento) < 0)
	    acomodaHaciaArriba(ultimo);
	 else if(ultimo.compareTo(elemento) > 0)
	 acomodaHaciaAbajo(ultimo);*/
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return (elemento.getIndice() >= 0 &&
		elemento.getIndice() < elementos);
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        elementos = 0;
	for(int i = 0; i < elementos; i++) {
	    arbol[i].setIndice(-1);
	    arbol[i] = null;
	}
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        acomodaHaciaArriba(elemento.getIndice());
	acomodaHaciaAbajo(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        if(i < 0 || i >= elementos)
	    throw new NoSuchElementException();
	return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
	String s = "";
        for(int i = 0; i < elementos; i++)
	    s += String.format("%s, ", arbol[i]);
	return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
	if(monticulo.elementos != elementos)
	    return false;
	for(int i = 0; i < elementos; i++)
	    if(!monticulo.get(i).equals(arbol[i]))
		return false;
	return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        Lista<Adaptador<T>> l1 = new Lista<Adaptador<T>>();
	for(T elem : coleccion)
	    l1.agrega(new Adaptador<T>(elem));

	Lista<T> l2 = new Lista<T>();

	MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(l1);
	while(!monticulo.esVacia()) {
	    Adaptador<T> adaptador = monticulo.elimina();
	    l2.agrega(adaptador.elemento);
	}
	return l2;
    }
}
