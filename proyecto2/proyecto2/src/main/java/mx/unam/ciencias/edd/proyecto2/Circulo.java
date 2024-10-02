package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Circulo {

    private int radio = 20;
    private int font_size = 15;

    public int radio() {
	return radio;
    }
    
    public int ancho() {
	return radio * 2;
    }

    public String
	toString(int cx, int cy, String color, String textColor, int elem) {
        String s =
	    "<circle cx=\""+cx+"\" cy=\""+cy+"\" r=\""+radio+"\" " +
	    "stroke=\"black\" stroke-width=\"2\" fill=\""+color+"\" />\n" +
	    "<text fill=\""+textColor+"\" font-family=\"sans-serif\" " +
	    "font-size=\""+font_size+"\" x=\""+cx+"\" y=\""+(cy+5)+"\" " +
	    "text-anchor=\"middle\">"+elem+"</text>\n";
	return s;
    }

}
