package mx.unam.ciencias.edd.proyecto1;

import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Lista;

/**
 * <p>Clase para procesador de argumentos.</p>
 *
 * <p>El procesador nos permite clasificar una serie de argumentos. 
 * Determina si existen las banderas "-r" y "-o" entre los argumentos, y en el
 * caso de existir la bandera "-o" establece un identificador. El resto de los
 * argumentos se esteblecen como nombres de archivos.</p>
 */
public class ProcesadorDeArgumentos {

    /* Estado de la banderaR. */
    private Boolean banderaR;
    /* Estado de la banderaO. */
    private Boolean banderaO;
    /* Nombre del identificador. */
    private String identificador;
    /* Lista de archivos. */
    private Lista<String> archivos;
    
    /**
     * Constuye un procesador de argumentos. 
     */
    public ProcesadorDeArgumentos() {
	banderaR = false;
	banderaO = false;
	archivos = new Lista<String>();
    }

    /**
     * Regresa el estado de la banderaR.
     * @return el estado de la banderaR, <True> si existe la bandera y <False>  
     * si no existe.
     */
    public Boolean getBanderaR() {
	return banderaR;
    }

    /**
     * Regresa el estado de la banderaO.
     * @return el estado de la banderaO, <True> si existe la bandera y <False>
     * si no existe.
     */
    public Boolean getBanderaO() {
	return banderaO;
    }

    /**
     * Regresa el nombre del identificador.
     * @return el nombre del identificador.
     */
    public String getIdentificador() {
	return identificador;
    }

    /**
     * Regresa la longitud de la lista de archivos.
     * @return la longitud de la lista de archivos, el número de archivos.
     */
    public int getNumeroDeArchivos() {
	return archivos.getLongitud();
    }

    /**
     * Regresa la lista de archivos.
     * @return la lista de archivos, los nombres de los archivos.
     */
    public Lista<String> getArchivos() {
	return archivos;
    }

    /**
     * Procesa un arreglo de argumentos, determinando si existe la bandera "-r",
     * la bandera "-o" y su respectivo identificador, y almacenando el resto de
     * los argumentos en una lista de archivos.
     */    
    public void procesa(String[] args) {
	for (int i = 0; i < args.length; i++) {
	    if (args[i].equals("-r"))
	        banderaR = true;
	    
	    else if (args[i].equals("-o")) {
		if (i+1 == args.length || args[i+1].equals("-r"))
		    throw new NoSuchElementException();
		banderaO = true;
		identificador = args[i+1];
		
	    } else if (i == 0 || !args[i-1].equals("-o"))
	        archivos.agregaFinal(args[i]);
	}
    }
}
