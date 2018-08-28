package Automata;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Automata automata = new Automata();
		ArrayList<String> pila = new ArrayList<String>();
		pila.add("Z0");
		ArrayList<Estado> camino = new ArrayList<Estado>();
		automata.probarCadena("aabb", 0, 0, pila, camino);
		
	}

}
