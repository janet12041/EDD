package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class VerticeArreglo {

    private MonticuloMinimo<IntegerIndexable> monticulo;
    private int i;
    private int x;
    private int y;
    private int d;

    private static Circulo c = new Circulo();
    private static Linea l = new Linea();

    public VerticeArreglo(MonticuloMinimo<IntegerIndexable> monticulo,
			     int i, int x, int y, int d) {
	this.monticulo = monticulo;
	this.i = i;
	this.x = x;
	this.y = y;
	this.d = d;
    }

    public int indice() {
	return i;
    }

    public boolean hayIzquierdo() {
	return (2*i)+1 < monticulo.getElementos();
    }

    public boolean hayDerecho() {
	return (2*i)+2 < monticulo.getElementos();
    }

    public VerticeArreglo izquierdo() {
	return new VerticeArreglo(monticulo, indiceI(), xhi(), yh(), Math.round(d/2));
    }

    public VerticeArreglo derecho() {
	return new VerticeArreglo(monticulo, indiceD(), xhd(), yh(), Math.round(d/2));
    }

    public int indiceI() {
	return (2*i)+1;
    }
    
    public int indiceD() {
	return (2*i)+2;
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
	String s = "";
	
	if(hayIzquierdo()) {
	    s += l.toString(x, y, xhi(), yh());
	}
	if(hayDerecho()) {
	    s += l.toString(x, y, xhd(), yh());
	}
	s += c.toString(x, y, "white", "black", monticulo.get(i).get());

	return s;
    }

}
