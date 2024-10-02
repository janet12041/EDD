package mx.unam.ciencias.edd.proyecto1;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.text.Collator;
import mx.unam.ciencias.edd.Lista;

/**
 * Proyecto 1: Ordenador lexicográfico.
 */
public class Proyecto1 {

    /* Imprime el uso del programa y termina. */
    private static void uso() {
        System.err.println("Uso: \n" +
	"java -jar target/proyecto1.jar [-r][-o <identificador>] " +
	"<archivo>... \n o cat <archivo>... | java -jar target/proyecto1.jar " +
	"[-r][-o <identificador>]");
        System.exit(1);
    }

    public static void main(String[] args) {

	ProcesadorDeArgumentos procesador = new ProcesadorDeArgumentos();
        Lineas lineas = new Lineas();

	/* Procesa los argumentos de entrada y carga los archivos por líneas. */
	try {
	    procesador.procesa(args);
	    
	    if (procesador.getNumeroDeArchivos() > 0) 
	        for (String archivo : procesador.getArchivos()) 
		    lineas.carga(archivo);
            else 
		lineas.carga(null);
	    
	}catch (NoSuchElementException e) {
	    uso();
        }catch (IOException ioe) {
            System.exit(1);
	}
    

	/* Ordena lexicográficamente las lineas. */
	Collator comparador = Collator.getInstance();
	comparador.setStrength(Collator.PRIMARY);

	Lista<String> lista = new Lista<String>();

	if(procesador.getBanderaR()) 
	    lista = lineas.getLineas().mergeSort((l1, l2) -> {
		    l1= l1.replaceAll("[^\\p{L}\\p{Z}]", "");
		    l2= l2.replaceAll("[^\\p{L}\\p{Z}]", "");
		    if(comparador.compare(l1, l2) < 0)
			return 1;
		    else if (comparador.compare(l1, l2) > 0)
			return -1;
		    else
			return 0;
	    });
	 else 
	     lista = lineas.getLineas().mergeSort((l1, l2) -> {
		    l1= l1.replaceAll("[^\\p{L}\\p{Z}]", "");
		    l2= l2.replaceAll("[^\\p{L}\\p{Z}]", "");
		    return comparador.compare(l1, l2);
	    });

	lineas.setLineas(lista);

	/* Guarda o muestra a pantalla las lineas. */
	if(procesador.getBanderaO()) {
	    try {
		lineas.guarda(procesador.getIdentificador());
	    } catch (IOException ioe) {
		System.exit(1);
	    }
	}else 
	    for (String linea : lineas.getLineas())
		System.out.println(linea);
	    
    }
}
