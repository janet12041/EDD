package mx.unam.ciencias.edd.proyecto1;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import mx.unam.ciencias.edd.Lista;

/**
 * <p>Clase para líneas .</p>
 *
 * <p>Lineas nos permite tener una lista de cadenas, estás se pueden cargar de 
 * un archivo, establecer una nueva lista de cadenas y guardarlas en un archivo
 * especificado.</p>
 */
public class Lineas {
    
    /* Lista de lineas. */
    private Lista<String> lineas = new Lista<String>();

    /**
     * Regresa la lista de líneas.
     * @return la lista de líneas.
     */
    public Lista<String> getLineas() {
	return lineas;
    }

    /**
     * Actualiza la lista de líneas con otra lista.
     * @param lineas la lista de cadenas por la que se actualizará la lista.
     */
    public void setLineas(Lista<String> lineas) {
	this.lineas = lineas;
    }

    /**
     * Carga un archivo a la lista de líneas.
     * @param archivo el archivo que se desea cargar.
     */
    public void carga(String archivo) throws IOException {
	try{
	    InputStreamReader isIn;
      
	    if (archivo == null) 
		isIn= new InputStreamReader(System.in);
	    else
		isIn= new InputStreamReader(new FileInputStream(archivo));
	   
	    BufferedReader in = new BufferedReader(isIn);
	    String linea;

	    if (!in.ready())
		throw new NoSuchElementException();

	    while ((linea = in.readLine()) != null) 
		lineas.agregaFinal(linea);

	    in.close();
	 }catch (IOException e){
	    System.out.printf("No pude cargar el archivo \"%s\".\n",
                              archivo);
	    throw new IOException();
	 }
    }

    /**
     * Guarda la lista de líneas.
     * @param identificador el nombre del archivo donde se guardará la lista.
     */
    public void guarda(String identificador) throws IOException {
	File archivo = new File(identificador);

	try{
	    FileOutputStream fileOut = new FileOutputStream(identificador);
	    OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
	    BufferedWriter out = new BufferedWriter(osOut);
	    
	    for (String linea : lineas)
		out.write(linea + "\n");
	    
	    out.close();
	} catch (IOException ioe) {
		System.out.printf("No pude guardar en el archivo \"%s\".\n",
                                  identificador);
	}
    }
}
