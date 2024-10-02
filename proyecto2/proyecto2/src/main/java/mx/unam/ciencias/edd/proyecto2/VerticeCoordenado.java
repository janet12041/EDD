package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class VerticeCoordenado {

    private VerticeArbolBinario<Integer> vertice;
    private int x;
    private int y;
    private int d;

    private static Circulo c = new Circulo();
    private static Linea l = new Linea();

    public VerticeCoordenado(VerticeArbolBinario<Integer> vertice,
			     int x, int y, int d) {
	this.vertice = vertice;
	this.x = x;
	this.y = y;
	this.d = d;
    }

    public VerticeArbolBinario<Integer> vertice() {
	return vertice;
    }

    public boolean hayIzquierdo() {
	return vertice.hayIzquierdo();
    }

    public boolean hayDerecho() {
	return vertice.hayDerecho();
    }

    public VerticeCoordenado izquierdo() {
	return new VerticeCoordenado(vertice.izquierdo(), xhi(), yh(), Math.round(d/2));
    }

    public VerticeCoordenado derecho() {
	return new VerticeCoordenado(vertice.derecho(), xhd(), yh(), Math.round(d/2));
    }

    public int x() {
	return x;
    }

    public int y() {
	return y;
    }

    public int xhi() {
	return  x - d;
    }

    public int xhd() {
	return  x + d;
    }

    public int yh() {
	return y + c.ancho() + 10;
    }
    
    public String toString() {
	return toString("white");
    }

    public String toString(String color) {
	String textColor = color != "white" ? "white" : "black";

	String s = "";
	
	if(vertice.getClass().getSimpleName().equals("VerticeAVL"))
	    s += etiquetaAVL();
	
	if(vertice.hayIzquierdo()) {
	    s += l.toString(x, y, xhi(), yh());
	}
	if(vertice.hayDerecho()) {
	    s += l.toString(x, y, xhd(), yh());
	}
	s += c.toString(x, y, color, textColor, vertice.get());

	return s;
    }

    public String etiquetaAVL() {
	int hi = vertice.hayIzquierdo() ? vertice.izquierdo().altura() : -1;
	int hd = vertice.hayDerecho() ? vertice.derecho().altura() : -1;
	String text = "(" + vertice.altura() + "/" + (hi-hd) + ")";

	int dx = esIzquierdo() ?  x-c.radio()/2 :
	    !vertice.hayPadre() ? x : x+c.radio()/2;
	int dy = !vertice.hayPadre() ?  y-c.radio()-7 : y-c.radio()-5;
	return "<text fill=\"black\" font-family=\"sans-serif\" " +
	    "font-size=\"10\" x=\""+ dx +"\" " +
	    "y=\""+dy+"\" text-anchor=\"middle\">"+text+"</text>\n";
    }

    public boolean esIzquierdo() {
	if(!vertice.hayPadre() || !vertice.padre().hayIzquierdo() ||
	   vertice.padre().izquierdo() != vertice)
	    return false;
	return true;
    }

}
