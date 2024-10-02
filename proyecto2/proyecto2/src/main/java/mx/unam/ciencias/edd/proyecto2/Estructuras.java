package mx.unam.ciencias.edd.proyecto2;

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
import mx.unam.ciencias.edd.*;

/**
 * <p>Clase para estructuras .</p>
 *
 * <p>Estructuras nos permite tener una estructura de datos (listas, arreglos, 
 * pilas, colas, árboles binarios, árboles binarios completos, árboles binarios
 * ordenados, árboles rojinegros, árboles AVL, gráficas y montículos mínimos) a
 * partir de un archivo y graficarla.</p>
 */
public class Estructuras {

    Lista<String> estructuras = new Lista<String>();

    String estructura;
    Lista<Integer> elementos = new Lista<Integer>();
    int ancho = 800;
    int alto = 500;
    
    public Estructuras() {
	estructuras.agrega("Lista");
	estructuras.agrega("Arreglo");
	estructuras.agrega("Pila");
	estructuras.agrega("Cola");
	estructuras.agrega("ArbolBinario");
	estructuras.agrega("ArbolBinarioCompleto");
	estructuras.agrega("ArbolBinarioOrdenado");
	estructuras.agrega("ArbolRojinegro");
	estructuras.agrega("ArbolAVL");
	estructuras.agrega("Grafica");
	estructuras.agrega("MonticuloMinimo");
	estructuras.agrega("MonticuloArreglo");
    }

    /**
     * Carga un archivo o la entrada estándar a la lista de líneas.
     * @param archivo el archivo que se desea cargar.
     */
    public void carga(String archivo) {
	try{
	    InputStreamReader isIn;

	    /* Obtenemos lo que se pasa por entrada estándar. */
	    if (archivo == "") 
		isIn= new InputStreamReader(System.in);
	    /* Obtenemos el archivo. */
	    else
		isIn= new InputStreamReader(new FileInputStream(archivo));
	   
	    BufferedReader in = new BufferedReader(isIn);
	    
	    if(in != null) {
		String linea;
		while ((linea = in.readLine()) != null)
		    procesa(linea);
	    } /*else {
		System.out.println("Es necesario ingresar el nombre de la "
				  + "estructura de datos y sus elementos");
		System.exit(1);
		}*/
	    in.close();
	    
	 }catch (IOException e){
	    System.out.printf("No pude cargar el archivo \"%s\".\n",
                              archivo);
	    System.exit(1);
	 }
    }

    /** 
     * Procesa una linea de texto para definir el nombre de la estructura 
     * y sus elementos. 
     * @param linea la linea de texto.
     */
    private void procesa(String linea) {

	/* Elimina todos los caracteres después de un "#". */
	linea = linea.replaceAll("#.*$","");

	String arr[] = new String[2];
	arr[1] = linea;

	do {
	    arr = arr[1].split("\\s+", 2);
	    if(!arr[0].isEmpty()) {
		
		/* Define el nombre de la estructura. */
		if(estructura == null) {
		    if(!estructuras.contiene(arr[0])) {
			System.out.println("El nombre de la estructura"
					   + " de datos es inválida.\n");
			System.out.println("Estructuras válidas: ");
			for(String e : estructuras)
			    System.out.println(e);
			System.exit(1);
		    }
		    estructura = arr[0];
			
		/* Agrega un elemento a la lista de elementos. */
		} else {
		    try {
			elementos.agrega(Integer.parseInt(arr[0]));
		    } catch(NumberFormatException e) {
			System.out.println("Los elementos de la estructura"
					  + "de datos deben ser enteros.");
			System.exit(1);
		    }
		}
		    
	    }
	} while(arr.length == 2 && !arr[1].isEmpty());

	if(estructura == "Grafica" && elementos.getLongitud()%2 != 0) {
	    System.out.println("El número de elementos debe ser par.");
	    System.exit(1);
	}
    }

    /**
     * Grafica la estructura cargada.
     */
    public void grafica() {

	String s = "";
     
        switch(estructura) {
	case "Lista": s += graficaLista();
	    break;
	case "Arreglo": s += graficaArreglo();
	    break;
	case "Pila": s += graficaPila();
	    break;
	case "Cola": s += graficaCola();
	    break;
	case "ArbolBinario": s += graficaArbolBinarioOrdenado();
	    break;
	case "ArbolBinarioCompleto": s += graficaArbolBinarioCompleto();
	    break;
	case "ArbolBinarioOrdenado": s += graficaArbolBinarioOrdenado();
	    break;
	case "ArbolRojinegro": s += graficaArbolRojinegro();
	    break;
	case "ArbolAVL": s += graficaArbolAVL();
	    break;
	case "Grafica": s+= graficaGrafica();
	    break;
	case "MonticuloMinimo": s += graficaMonticuloMinimo();
	    break;
	case "MonticuloArreglo": s += graficaMonticuloMinimo();
	    break;
	}

	s = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
	    "<svg width=\""+ancho+"\" height=\""+alto+"\">\n" +
	    "<g>\n" +
	    s +
	    "</g>\n" +
	    "</svg>";
 
	System.out.println(s);
    }

    private String graficaLista() {
	String s = "";
	int x = 4;
	int y = 4;

	Rectangulo r = new Rectangulo();
	Flecha f = new Flecha();

	IteradorLista<Integer> iterador = elementos.iteradorLista();
	while(iterador.hasNext()) {
	    s += r.toString(x, y, iterador.next());
	    if(iterador.hasNext())
		s += f.toString(x+r.ancho(),y+r.alto()/2 ,
				x+r.ancho()+20 , y+r.alto()/2, true, true);

	    x = x+r.ancho()+20 < ancho-50 ? x+r.ancho()+20 : 4;
	    y = x != 4 ? y : y+r.alto()+10 ;
	}
	alto = y+r.alto()+10;
	return s;
    }

    private String graficaArreglo() {
	String s = "";
	int x = 15;
	int y = 15;

	Rectangulo r = new Rectangulo();

	IteradorLista<Integer> iterador = elementos.iteradorLista();
	int c = 0;
	while(iterador.hasNext()) {
	    s += r.toString(x, y, iterador.next());
	    s += "<text fill=\"black\" font-family=\"sans-serif\" " +
		"font-size=\"10\" x=\""+(x+r.ancho()/2)+"\" " +
		"y=\""+(y-3)+"\" text-anchor=\"middle\">"+c+"</text>\n";

	    x = x+r.ancho() < ancho-50 ? x+r.ancho() : 15;
	    y = x != 15 ? y : y+r.alto()+15 ;
	    c++;
	}
	alto = y+r.alto()+15;
	
	return s;
    }

    public String graficaPila() {
	String s = "";
	int x = 4;
	int y = 4;

	Rectangulo r = new Rectangulo();
	Flecha f = new Flecha();

	IteradorLista<Integer> iterador = elementos.iteradorLista();
	iterador.end();
	while(iterador.hasPrevious()) {
	    s += r.toString(x, y, iterador.previous());
	    if(iterador.hasPrevious())
		s += f.toString(x+r.ancho()/2,y+r.alto() ,
			       x+r.ancho()/2 , y+r.alto()+20, true, false);

	    y = y+r.alto()+20 < alto-50 ? y+r.alto()+20 : 4;
	    x = y != 4 ? x : x+r.ancho()+10;
	}
	ancho = x+r.ancho()+10;
	return s;
    }

    public String graficaCola() {
	String s = "";
	int x = 4;
	int y = 4;

	Rectangulo r = new Rectangulo();
	Flecha f = new Flecha();

	IteradorLista<Integer> iterador = elementos.iteradorLista();
	while(iterador.hasNext()) {
	    s += r.toString(x, y, iterador.next());
	    if(iterador.hasNext())
		s += f.toString(x+r.ancho(),y+r.alto()/2 ,
			       x+r.ancho()+20 , y+r.alto()/2, true, false);

	    x = x+r.ancho()+20 < ancho-50 ? x+r.ancho()+20 : 4;
	    y = x != 4 ? y : y+r.alto()+10 ;
	}
	alto = y+r.alto()+10;
	return s;
    }

    public String graficaArbolBinario(Cola<VerticeCoordenado> cola) {
	String s = "";
	
	int y = 0;

	while(!cola.esVacia()) {
	    VerticeCoordenado v = cola.saca();
	    s += v.toString();
	    if(v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if(v.hayDerecho())
		cola.mete(v.derecho());

	    if(v.y() > y)
	    y = v.y();
	}

	alto = y + 40;
	
	return s;
    }

    private String graficaArbolBinarioOrdenado() {
	if(elementos.getLongitud() == 0)
	    return "";
	
	ArbolBinarioOrdenado<Integer> arbol =
	    new ArbolBinarioOrdenado<Integer>(elementos);

	ancho =  (int)(50 *  Math.pow(2, arbol.altura()) + 60);
	
	Cola<VerticeCoordenado> cola = new Cola<VerticeCoordenado>();
	cola.mete(new VerticeCoordenado(arbol.raiz(), Math.round(ancho/2),
					30, Math.round(ancho/4)));
	
	return graficaArbolBinario(cola);
    }

    private String graficaArbolBinarioCompleto() {
	if(elementos.getLongitud() == 0)
	    return "";
	
	ArbolBinarioCompleto<Integer> arbol =
	    new ArbolBinarioCompleto<Integer>(elementos);

	ancho =  (int)(50 *  Math.pow(2, arbol.altura()) + 60);
	
	Cola<VerticeCoordenado> cola = new Cola<VerticeCoordenado>();
	cola.mete(new VerticeCoordenado(arbol.raiz(), Math.round(ancho/2),
					30, Math.round(ancho/4)));

	return graficaArbolBinario(cola);
    }

    private String graficaArbolRojinegro() {
	if(elementos.getLongitud() == 0)
	    return "";
	
	ArbolRojinegro<Integer> arbol =
	    new ArbolRojinegro<Integer>(elementos);

	ancho =  (int)(50 *  Math.pow(2, arbol.altura()) + 60);
	
	Cola<VerticeCoordenado> cola = new Cola<VerticeCoordenado>();
	cola.mete(new VerticeCoordenado(arbol.raiz(), Math.round(ancho/2),
					30, Math.round(ancho/4)));
	
        String s = "";

	int y = 0;

	while(!cola.esVacia()) {
	    VerticeCoordenado v = cola.saca();
	    String color = arbol.getColor(v.vertice()) == Color.NEGRO ?
		"black" : "red";
	    s += v.toString(color);
	    if(v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if(v.hayDerecho())
		cola.mete(v.derecho());

	    if(v.y() > y)
		y = v.y();
	}

	alto = y + 40;
	
	return s;
    }

    private String graficaArbolAVL() {
	if(elementos.getLongitud() == 0)
	    return "";
	
	ArbolAVL<Integer> arbol =
	    new ArbolAVL<Integer>(elementos);

	ancho =  (int)(50 *  Math.pow(2, arbol.altura()) + 60);
	
	Cola<VerticeCoordenado> cola = new Cola<VerticeCoordenado>();
	cola.mete(new VerticeCoordenado(arbol.raiz(), Math.round(ancho/2),
					50, Math.round(ancho/4)));
	
	return graficaArbolBinario(cola);
    }

    private String graficaMonticuloMinimo() {
	if(elementos.getLongitud() == 0)
	    return "";

	Lista<IntegerIndexable> index = new Lista<IntegerIndexable>();
	for(int elem : elementos)
	    index.agrega(new IntegerIndexable(elem));
	
	MonticuloMinimo<IntegerIndexable> monticulo =
	    new MonticuloMinimo<IntegerIndexable>(index);

	int t = elementos.getLongitud();
	//int altura = (int)(Math.floor(Math.log(t)/Math.log(2)));
	//ancho =  (int)(50 *  Math.pow(2, altura + 60));

	Cola<VerticeArreglo> cola = new Cola<VerticeArreglo>();
	cola.mete(new VerticeArreglo(monticulo, 0, Math.round(ancho/2),
					30, Math.round(ancho/4)));
	
        String s = "";
	int y = 0;

	while(!cola.esVacia()) {
	    VerticeArreglo v = cola.saca();
	    s += v.toString();
	    if(v.hayIzquierdo())
		cola.mete(v.izquierdo());
	    if(v.hayDerecho())
		cola.mete(v.derecho());
	    y = v.y();
	}
	
	int x = 15;
	y = y + 50;
	//arreglo

	Rectangulo r = new Rectangulo();

        for(int i = 0; i < monticulo.getElementos(); i++) {
	    s += r.toString(x, y, monticulo.get(i).get());
	    s += "<text fill=\"black\" font-family=\"sans-serif\" " +
		"font-size=\"10\" x=\""+(x+r.ancho()/2)+"\" " +
		"y=\""+(y-3)+"\" text-anchor=\"middle\">"+i+"</text>\n";

	    x = x+r.ancho() < ancho-50 ? x+r.ancho() : 15;
	    y = x != 15 ? y : y+r.alto()+15 ;
	}

	alto = y+r.alto()+15;
	
	return s;
    }

    private String graficaGrafica() {
	if(elementos.getLongitud() == 0)
	    return "";

	Circulo c = new Circulo();
	Linea l = new Linea();

	String s = "";

	Lista<Integer> vertices = new Lista<Integer>();

	for(Integer elem : elementos) {
	    if(!vertices.contiene(elem))
		vertices.agrega(elem);
	}

	int numV = vertices.getElementos();

	int[] xs = new int[numV];
	int[] ys = new int[numV];

	int r = Math.round((alto- 2 *c.ancho())/2);
	//int x1 = Math.round(ancho/2)-c.ancho();
	//int x2 = Math.round(ancho/2)+c.ancho();
	int y = c.ancho() * 2 - 30;
	int yc;
	int xc;
	//int x;
	
	for(int j = 0; j < numV; j++) {
	    yc = Math.round(alto/2) - y;
	    xc = (int)Math.round(Math.sqrt(Math.pow(r, 2) - Math.pow(yc, 2)));
	    xs[j] = xc + Math.round(ancho/2) + j;
	    ys[j] = y;

	    if(j+1 != numV) {
		j++;
		xs[j] = Math.round(ancho/2) - xc -j;
		ys[j] = ys[j-1];
	    }
	    
	    y += Math.round(((alto - 2 * c.ancho())/Math.ceil((numV)/2)));
		
	}

        IteradorLista<Integer> iterador = elementos.iteradorLista();
	while(iterador.hasNext()) {
	    int elem1 = iterador.next();
	    int elem2 = iterador.next();

	    if(elem1 != elem2) {
		int i1 = vertices.indiceDe(elem1);
		int i2 = vertices.indiceDe(elem2);
		s+= l.toString(xs[i1], ys[i1], xs[i2], ys[i2]);
	    }
	}

	int cont = 0;
	for(int v : vertices) { 
	    s+= c.toString(xs[cont], ys[cont], "white","black", v);
	    cont++;
	}
	
	return s;
    }
}
