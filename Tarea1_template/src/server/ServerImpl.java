package server;

import common.InterfazDeServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import common.Persona;

public class ServerImpl implements InterfazDeServer {
	
	private ArrayList<Persona> BD_personas = new ArrayList<>();
	
	public ServerImpl() throws RemoteException{
		UnicastRemoteObject.exportObject((Remote) this,0);
		crearBD();
		
	}
	
	private void crearBD() {
		Persona persona1 = new Persona("Kirito",21);
		Persona persona2 = new Persona("Pablo",25);
		
		BD_personas.add(persona1);
		BD_personas.add(persona2);
		System.out.println("Base de datos inicial creada con 2 personas.");
	}
	
	public ArrayList<Persona> getPersona() throws RemoteException {
        System.out.println("Cliente solicitó la lista de personas. Enviando " + BD_personas.size() + " personas.");
        return BD_personas;
    }
	@Override 
	public void agregarPersona(String nombre,int edad) throws RemoteException{
		
		Persona nuevaPersona = new Persona(nombre,edad);
		BD_personas.add(nuevaPersona);
		System.out.println("Se agrego la persona con los siguientes datos: " + nombre+"," + edad );
		
	}
	
}
