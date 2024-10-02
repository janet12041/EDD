package mx.unam.ciencias.edd.proyecto2;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Proyecto 2: Garficador de estructuras de datos.
 */
public class Proyecto2 {

    /* Imprime el uso del programa y termina. */
    private static void uso() {
        System.err.println("Uso: \n" +
	"java -jar target/proyecto2.jar [archivo] " +
	"\no cat [archivo]| java -jar target/proyecto2.jar ");
        System.exit(1);
    }

    public static void main(String[] args) {

	if(args.length > 1)
	    uso();

	/* Nombre del archivo obtenido del argumrnto recibido. */
	String nombreArchivo = args.length == 1 ? args[0] : "";

	/* Objeto para cargar las estructuras de datos. */
        Estructuras estructuras = new Estructuras();

	
	/* Carga el archivo pasado por argumento o entrada estándar a 
	       estructuras. */
	estructuras.carga(nombreArchivo);

        estructuras.grafica();
	    
    }
}
