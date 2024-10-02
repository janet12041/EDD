package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
	    color = Color.NINGUNO;
	    vecinos = new Lista<Vertice>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null || contiene(elemento))
	    throw new IllegalArgumentException();
	Vertice vertice = new Vertice(elemento);
	vertices.agrega(vertice);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        Vertice v1 = (Vertice)vertice(a);
	Vertice v2 = (Vertice)vertice(b);
	if(v1 == v2 || sonVecinos(a,b))
	    throw new IllegalArgumentException();
	v1.vecinos.agrega(v2);
	v2.vecinos.agrega(v1);
	aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice v1 = (Vertice)vertice(a);
	Vertice v2 = (Vertice)vertice(b);
	if(!sonVecinos(a,b))
	    throw new IllegalArgumentException();
	v1.vecinos.elimina(v2);
	v2.vecinos.elimina(v1);
	aristas--;
    }

    private VerticeGrafica<T> getVertice(T elemento) {
	if(elemento == null)
	    return null;
	for(Vertice vertice : vertices)
	    if(vertice.elemento.equals(elemento))
	        return vertice;
	return null;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return getVertice(elemento) != null; 
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = (Vertice)vertice(elemento);
	vertices.elimina(vertice);
	//seguro?
	for(Vertice v : vertice.vecinos) {
	    v.vecinos.elimina(vertice);
	    aristas--;
	}
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice v1 = (Vertice)vertice(a);
	Vertice v2 = (Vertice)vertice(b);
	return (v1.vecinos.contiene(v2) && v2.vecinos.contiene(v1));
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        VerticeGrafica<T> vertice = getVertice(elemento);
	if(vertice == null)
	    throw new NoSuchElementException();
        return vertice;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || Vertice.class != vertice.getClass())
            throw new IllegalArgumentException();
        @SuppressWarnings("unchecked") Vertice v = (Vertice)vertice;
	v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        paraCadaVertice(v -> ((Vertice)v).color = Color.ROJO);
	Vertice vertice = vertices.getPrimero();
	Pila<Vertice> pila = new Pila<Vertice>();
	
	vertice.color = Color.NEGRO;
	pila.mete(vertice);
	while(!pila.esVacia()) {
	    Vertice v1 = pila.saca();
	    for (Vertice v2 : v1.vecinos) {
		if(v2.color == Color.ROJO) {
		    v2.color = Color.NEGRO;
		    pila.mete(v2);
		}
	    }
	}
	for(Vertice v : vertices)
	    if(v.color == Color.ROJO) {
		paraCadaVertice(ver ->
			 ((Vertice)ver).color = Color.NINGUNO);
		return false;
	    }
	paraCadaVertice(ver -> ((Vertice)ver).color = Color.NINGUNO);
	return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for(Vertice vertice : vertices)
	    accion.actua(vertice);
    }

    private void auxiliar(MeteSaca<Vertice> metesaca, Vertice vertice,
		     AccionVerticeGrafica<T> accion) {
	paraCadaVertice(v -> ((Vertice)v).color = Color.ROJO);
	vertice.color = Color.NEGRO;
	metesaca.mete(vertice);
	while(!metesaca.esVacia()) {
	    Vertice v1 = metesaca.saca();
	    accion.actua(v1);
	    for (Vertice v2 : v1.vecinos) {
		if(v2.color == Color.ROJO) {
		    v2.color = Color.NEGRO;
		    metesaca.mete(v2);
		}
	    }
	}
	paraCadaVertice(v -> ((Vertice)v).color = Color.NINGUNO);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = (Vertice)vertice(elemento);
	Cola<Vertice> cola = new Cola<Vertice>();
	auxiliar(cola, vertice, accion);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        Vertice vertice = (Vertice)vertice(elemento);
	Pila<Vertice> pila = new Pila<Vertice>();
	auxiliar(pila, vertice, accion);
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
	aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        paraCadaVertice(v -> ((Vertice)v).color = Color.ROJO);
	String vertices = "";
	String aristas = "";
	
	for(Vertice vertice : this.vertices) {
	    vertice.color = Color.NEGRO;
	    vertices+= String.format("%s, ", vertice.elemento);
	    for(Vertice vecino : vertice.vecinos)
		if(vecino.color == Color.ROJO)
		    aristas+= String.format("(%s, %s), ",
			      vertice.elemento, vecino.elemento);
	}
	paraCadaVertice(v -> ((Vertice)v).color = Color.NINGUNO);
        return "{" + vertices + "}, " + "{" + aristas + "}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        if(getElementos() != grafica.getElementos() ||
	   aristas != grafica.aristas)
	    return false;
	
	paraCadaVertice(v -> ((Vertice)v).color = Color.ROJO);
	for(Vertice vertice : vertices) {
	    try {
		vertice.color = Color.NEGRO;
		Vertice v = (Vertice)(grafica.vertice(vertice.elemento));
		for(Vertice vecino : vertice.vecinos)
		    if(vecino.color == Color.ROJO) {
			Vertice vec =
			    (Vertice)(grafica.vertice(vecino.elemento));
			if(!v.vecinos.contiene(vec) ||
			   !vec.vecinos.contiene(v)) {
			    paraCadaVertice(ver ->
				   ((Vertice)ver).color = Color.NINGUNO);
			    return false;
			}
		    }
	    } catch(NoSuchElementException e) {
		paraCadaVertice(ver -> ((Vertice)ver).color = Color.NINGUNO);
		return false;
	    }
	}
	paraCadaVertice(ver -> ((Vertice)ver).color = Color.NINGUNO);
	return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
