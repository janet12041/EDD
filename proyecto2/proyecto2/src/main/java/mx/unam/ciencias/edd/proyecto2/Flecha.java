package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

public class Flecha {

    private int anchoT = 4;
    private int altoT = 3;
    private Linea linea = new Linea();

    public String
	toString(int x1, int y1, int x2, int y2, boolean p1, boolean p2) {
        String s = linea.toString(x1, y1, x2, y2);
	String v1 = "";
	String v2 = "";
	String v3 = "";
	// arriba o izquierda
	if(p1) {
	    v2 = x1+","+y1+" ";
	    //vertical
	    if(x1 == x2) {
		v1 = (x1-altoT)+","+(y1+anchoT)+" ";
		v3 = (x1+altoT)+","+(y1+anchoT);
	    //horizontal
	    } else{
	        v1 = (x1+anchoT)+","+(y1-altoT)+" ";
		v3 = (x1+anchoT)+","+(y1+altoT);
	    }
	    
	    s += "<polygon points=\""+v1+v2+v3+"\" stroke=\"black\" " +
		"stroke-width=\"2\" />\n";
	}
	
	//abajo o derecha
	if(p2) {
	    v2 = x2+","+y2+" ";
	    //vertical
	    if(x1 == x2) {
		v1 = (x1-altoT)+","+(y1-anchoT)+" ";
		v3 = (x1+altoT)+","+(y1-anchoT);
	    //horizontal
	    } else{
	        v1 = (x2-anchoT)+","+(y2-altoT)+" ";
		v3 = (x2-anchoT)+","+(y2+altoT);
	    }
	    
	    s += "<polygon points=\""+v1+v2+v3+"\" stroke=\"black\" " +
		"stroke-width=\"2\" />\n";
	}
	return s;
    }

}
