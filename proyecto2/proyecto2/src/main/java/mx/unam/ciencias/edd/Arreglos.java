package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Intercambia de lugar dos elementos del arreglo recibido.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a tratar.
     * @param i el indíce del elemento a intercambiar.
     * @param m el indíce del elemento a intercambiar.
     */
    private static <T> void intercambia(T[] arreglo, int i, int m) {
	T elem = arreglo[i];
	arreglo[i] = arreglo[m];
	arreglo[m] = elem;
    }

    /**
     * Ordena el subarreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param a el primer índice del subarreglo.
     * @param b el último índice del subarreglo.
     * @param comparador el comparador para ordenar el subarreglo.
     */
    private static <T> void
	quickSort(T[] arreglo, int a, int b, Comparator<T> comparador) {
	if (b <= a)
	    return;
	int i = a+1;
	int j = b;
	while (i < j) {
	    if (comparador.compare(arreglo[i], arreglo[a]) > 0
		&& comparador.compare(arreglo[j], arreglo[a]) <= 0) {
		intercambia(arreglo, i, j);
		i++;
		j--;
	    } else if (comparador.compare(arreglo[i], arreglo[a]) <= 0)
		i++;
	    else
		j--;
	}
	if (comparador.compare(arreglo[i], arreglo[a]) > 0)
	    i--;
	intercambia(arreglo, a, i);
	quickSort(arreglo, a, i-1, comparador);
	quickSort(arreglo, i+1, b, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, 0, arreglo.length -1, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
	    int m = i;
	    for (int j = i+1; j < arreglo.length; j++) {
		if (comparador.compare(arreglo[j], arreglo[m]) < 0)
		    m = j;
	    }
	    intercambia(arreglo, i, m);
	}
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el subarreglo. Regresa el 
     * índice del elemento en el subarreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el subarreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param a el primer índice del subarreglo.
     * @param b el último índice del subarreglo.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el subarreglo, o -1 si no se encuentra.
     */
    private static <T> int busquedaBinaria
    (T[] arreglo, int a, int b, T e, Comparator<T> comparador) {
	if (b < a)
	    return -1;
	int m = (a+b) % 2 == 0 ? (a+b)/2 : (a+b-1)/2;
	if (comparador.compare(e, arreglo[m]) == 0)
	    return m;
	else if (comparador.compare(e, arreglo[m]) < 0)
	    return busquedaBinaria(arreglo, a, m-1, e, comparador);
	else
	    return busquedaBinaria(arreglo, m+1, b, e, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        return busquedaBinaria(arreglo, 0, arreglo.length - 1, elemento, comparador);
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
