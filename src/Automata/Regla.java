package Automata;

public class Regla {

	public String entrada;
	public String tope;
	public String estadoFuturo;
	public String accion;

	

	public Regla(String entrada, String tope, String estadoFuturo, String accion) {

		this.entrada = entrada;
		this.tope = tope;
		this.estadoFuturo = estadoFuturo;
		this.accion = accion;
		
	}



	public Regla() {
		
		
	}
	
}
