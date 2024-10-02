package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Linea {

    public String toString(int x1, int y1, int x2, int y2) {
	String s =
	    "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" " +
	    "stroke=\"black\" stroke-width=\"2\" />\n";
	return s;
    }

}
