package Automata;

import java.util.ArrayList;

public class Automata {

	public ArrayList<Estado> estados;

	public String epsilon;

	public ArrayList<Estado> caminoAceptado;

	public Automata() {

		this.caminoAceptado = null;
		this.estados = new ArrayList<Estado>();
		this.epsilon = "e";
		this.estados.add(new Estado("leer", false));
		this.estados.add(new Estado("leerB", false));
		this.estados.add(new Estado("Accept", true));
		this.estados.get(0).reglas.add(new Regla("e", "Z0", "Accept", "nada"));
		this.estados.get(0).reglas.add(new Regla("a", "Z", "leer", "AA"));
		this.estados.get(0).reglas.add(new Regla("a", "Z", "leer", "A"));
		this.estados.get(0).reglas.add(new Regla("b", "A", "leerB", "Desapilar"));
		this.estados.get(1).reglas.add(new Regla("b", "A", "leerB", "Desapilar"));
		this.estados.get(1).reglas.add(new Regla("e", "Z0", "Accept", "nada"));

	}

	public void probarCadena(String cadena, int estadoAct, int posicionCadena, ArrayList<String> pil,
			ArrayList<Estado> camin) {

		// System.out.println("Probando cadena");

		ArrayList<ArrayList<String>> pila = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<Estado>> camino = new ArrayList<ArrayList<Estado>>();

		this.clonarArrays(estadoAct, pila, pil, camin, camino);

		for (int nroRegla = 0; nroRegla < this.estados.get(estadoAct).reglas.size(); nroRegla++) {

			// System.out.println("Numero de elemento de la cadena: " + posicionCadena);
			// System.out.println("Pila Actual: ");

			for (int i = 0; i < pila.get(nroRegla).size(); i++) {

			// 	System.out.println(pila.get(nroRegla).get(i));

			}

			String entrada = this.determinarEntrada(cadena, posicionCadena);
			boolean transicionEpsilon = this.tipoTransicion(estadoAct, nroRegla);
			int estadoFuturo = this.posicionEstado(this.estados.get(estadoAct).reglas.get(nroRegla).estadoFuturo);

			if (this.verificarRegla(estadoAct, entrada, nroRegla, pila.get(nroRegla), transicionEpsilon)) {

				pila.set(nroRegla, this.modificarPila(pila.get(nroRegla), nroRegla, estadoAct));
				camino.get(nroRegla)
						.add(new Estado(this.estados.get(estadoAct).nombre, this.estados.get(estadoAct).aceptacion));
				camino.get(nroRegla).get(camino.get(nroRegla).size() - 1).reglas
						.add(this.estados.get(estadoAct).reglas.get(nroRegla));

				if (this.verificarFinal(cadena, posicionCadena) && !transicionEpsilon) {

					if (this.verificarAceptacion(estadoAct, pila.get(nroRegla))) {

						System.out.println("Cadena Aceptada");
						this.caminoAceptado = camino.get(nroRegla);
						System.exit(0);

					}

				} else {

					if (transicionEpsilon) {

						this.probarCadena(cadena, estadoFuturo, posicionCadena, pila.get(nroRegla),
								camino.get(nroRegla));

					} else {

						this.probarCadena(cadena, estadoFuturo, (posicionCadena + 1), pila.get(nroRegla),
								camino.get(nroRegla));

					}

				}

			}

		}

	}

	public void clonarArrays(int estadoAct, ArrayList<ArrayList<String>> pila, ArrayList<String> pil,
			ArrayList<Estado> camin, ArrayList<ArrayList<Estado>> camino) {

		for (int i = 0; i < this.estados.get(estadoAct).reglas.size(); i++) {

			pila.add(new ArrayList<String>());

			for (int j = 0; j < pil.size(); j++) {

				pila.get(pila.size() - 1).add(pil.get(j));

			}

			camino.add(new ArrayList<Estado>());

			for (int j = 0; j < camin.size(); j++) {

				camino.get(camino.size() - 1).add(camin.get(j));

			}

		}

	}

	public ArrayList<String> modificarPila(ArrayList<String> pila, int nroRegla, int estadoAct) {

		switch (this.estados.get(estadoAct).reglas.get(nroRegla).accion) {

		case "nada":

			// System.out.println("nada que hacer en la pila");

			break;

		case "Desapilar":

			// System.out.println("Desapilando");
			pila.remove(pila.size() - 1);

			break;

		default:

			for (int i = 0; i < this.estados.get(estadoAct).reglas.get(nroRegla).accion.length(); i++) {

				// System.out.println(
				// 		"Agregando a la pila: " + this.estados.get(estadoAct).reglas.get(nroRegla).accion.charAt(i));
				pila.add(String.valueOf(this.estados.get(estadoAct).reglas.get(nroRegla).accion.charAt(i)));

			}

			break;

		}

		return pila;

	}

	public boolean verificarRegla(int estadoAct, String entrada, int nroRegla, ArrayList<String> pila,
			boolean transicionEpsilon) {

		String entradaEsperada = this.estados.get(estadoAct).reglas.get(nroRegla).entrada;
		String topeActual = pila.get(pila.size() - 1);
		String topeEsperado = this.estados.get(estadoAct).reglas.get(nroRegla).tope;
		// System.out.println("Transicion Epsilon: " + transicionEpsilon);
		// System.out.println("Entrada actual: " + entrada + " Entrada Esperada: " + entradaEsperada);
		// System.out.println("Tope actual: " + topeActual + " Tope Esperado: " + topeEsperado);

		if (transicionEpsilon) {

			if (topeActual.equals(topeEsperado)) {

				// System.out.println("Transicion epsilon Aceptada");
				return true;

			} else {

				// System.out.println("Transicion epsilon False");
				return false;

			}

		} else {

			if ((topeEsperado.equals("Z") || topeEsperado.equals(topeActual)) && entradaEsperada.equals(entrada)) {

				// System.out.println("Regla Aceptada");
				return true;

			} else {

				// System.out.println("Regla Rechazada");
				return false;

			}

		}

	}

	private int posicionEstado(String nombre) {

		int posicion = 0;

		for (int i = 0; i < this.estados.size(); i++) {

			if (this.estados.get(i).nombre.equals(nombre)) {

				posicion = i;
				break;

			}

		}

		return posicion;

	}

	private boolean verificarAceptacion(int estadoAct, ArrayList<String> pila) {

		boolean accept = this.estados.get(estadoAct).aceptacion;

		if (pila.size() == 1) {

			accept = true;

		}

		return accept;

	}

	private boolean verificarFinal(String cadena, int posicionCadena) {

		if (posicionCadena == (cadena.length() - 1)) {

			return true;

		} else {

			return false;

		}

	}

	public String determinarEntrada(String cadena, int posicionCadena) {

		if (cadena.length() == 0) {

			return "";

		} else {

			return String.valueOf(cadena.charAt(posicionCadena));

		}

	}

	public boolean tipoTransicion(int estadoAct, int nroRegla) {

		if (this.estados.get(estadoAct).reglas.get(nroRegla).entrada.equals(this.epsilon)) {

			return true;

		} else {

			return false;

		}

	}

}
