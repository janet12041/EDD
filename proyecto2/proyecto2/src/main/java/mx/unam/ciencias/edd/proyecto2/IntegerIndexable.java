package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class IntegerIndexable
    implements ComparableIndexable<IntegerIndexable> {

    /* El elemento. */
    private int elemento;
    /* El índice. */
    private int indice;

    /* Crea un nuevo comparable indexable. */
    public IntegerIndexable(int elemento) {
	this.elemento = elemento;
	indice = -1;
    }
    
    public int get(){
    	return elemento;
    }

    /* Regresa el índice. */
    @Override public int getIndice() {
	return indice;
    }

    /* Define el índice. */
    @Override public void setIndice(int indice) {
	this.indice = indice;
    }

    /* Compara un IntegerIndexable con otro. */
    @Override public int compareTo(IntegerIndexable intIndex) {
	return elemento - intIndex.elemento;
    }

}
