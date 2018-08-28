package Automata;

import java.util.ArrayList;

public class Estado {
	
	public String nombre;
	
	public boolean aceptacion;
	
	public ArrayList<Regla> reglas;
	
	public Estado(String nombre, boolean aceptacion) {
		
		this.nombre = nombre;
		this.aceptacion = aceptacion;
		this.reglas = new ArrayList<Regla>();

	}
	
	public void addRule(Regla regla) {
				
		this.reglas.add(regla);
		
	}

}
