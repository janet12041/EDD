package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Rectangulo {

    // ancho y altura pares
    private int ancho = 40;
    private int alto = 20;
    private int font_size = 15;

    public int ancho() {
	return ancho;
    }

    public int alto() {
	return alto;
    }

    public String toString(int x, int y, int elem) {
	String s =
	    "<rect x=\""+x+"\" y=\""+y+"\" width=\""+ancho+"\" " +
	    "height=\""+alto+"\" stroke=\"black\" stroke-width=\"2\" " +
	    "fill=\"white\"/>\n" +
	    "<text fill=\"black\" font-family=\"sans-serif\" " +
	    "font-size=\""+font_size+"\" x=\""+(x+ancho/2)+"\" " +
	    "y=\""+(y+15)+"\" text-anchor=\"middle\">"+elem+
	    "</text>\n";
	return s;
    }

}
