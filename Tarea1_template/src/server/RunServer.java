package server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.InterfazDeServer; 

public class RunServer {

    public static void main(String[] args) {
        try {
            int port = 1009; // puerto RMI

            InterfazDeServer serverImpl = new ServerImpl();
            
            Registry registry;//crea el registro RMI
            try {
                registry = LocateRegistry.createRegistry(port);
                System.out.println("Registro RMI creado en el puerto " + port);
            } catch (RemoteException e) {
                System.out.println("Registro RMI ya existe en el puerto " + port + ", obteniendo referencia.");
                registry = LocateRegistry.getRegistry(port);
            }

            //  Vincular la implementación al registro con un nombre
            registry.bind("server", (Remote) serverImpl);

            System.out.println("Servidor RMI listo y vinculado como 'server' en el puerto " + port);

        } catch (RemoteException e) {
            System.err.println("Error de RMI en el servidor: " + e.toString());
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            System.err.println("Error: El nombre 'server' ya está vinculado en el registro RMI.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Excepción en el servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}