package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException; // Para manejar errores de entrada
import java.util.Scanner;             // Para leer la entrada del usuario

public class RunClient {

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in); // Crear Scanner para entrada
        boolean salir = false;

        try {
            client.startClient(); // Intenta conectar con el servidor
            System.out.println("¡CLIENTE CONECTADO!");
            System.out.println("-------------------------");

            while (!salir) {
                // Mostrar menú
                System.out.println("\n--- Menú Cliente RMI ---");
                System.out.println("1. Agregar Persona");
                System.out.println("2. Ver Lista de Personas");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                int opcion = -1; // Valor inicial inválido

                // Leer opción del usuario y manejar errores si no es número
                try {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir el salto de línea restante
                } catch (InputMismatchException e) {
                    System.err.println("Error: Por favor, ingrese un número válido.");
                    scanner.nextLine(); // Limpiar el buffer del scanner
                    continue; // Volver al inicio del bucle while
                }


                // Procesar opción con switch
                switch (opcion) {
                    case 1:
                        // Lógica para agregar persona
                        System.out.print("Ingrese el nombre de la persona: ");
                        String nombre = scanner.nextLine();
                        int edad = -1;
                        while (edad < 0) { // Validar que la edad sea positiva
                            System.out.print("Ingrese la edad de la persona: ");
                            try {
                                edad = scanner.nextInt();
                                scanner.nextLine(); // Consumir salto de línea
                                if (edad < 0) {
                                    System.out.println("La edad no puede ser negativa.");
                                }
                            } catch (InputMismatchException e) {
                                System.err.println("Error: Ingrese una edad numérica válida.");
                                scanner.nextLine(); // Limpiar buffer
                            }
                        }
                        // Llamar al método del cliente para agregar en el servidor
                        try {
                             client.agregarPersonaAlServidor(nombre, edad);
                             System.out.println("Persona agregada (o solicitud enviada).");
                        } catch (RemoteException e) {
                             System.err.println("Error al intentar agregar persona en el servidor: " + e.getMessage());
                             // Considera si quieres reintentar o salir en caso de error RMI
                        }

                        break; // Salir del switch

                    case 2:
                        // Lógica para ver la lista
                        System.out.println("\nSolicitando lista de personas al servidor...");
                        try {
                            client.mostrarPersonas();
                        } catch (RemoteException e) {
                            System.err.println("Error al obtener la lista del servidor: " + e.getMessage());
                            // Considera acciones si falla la comunicación
                        }
                        break; // Salir del switch

                    case 0:
                        // Salir
                        salir = true;
                        System.out.println("Desconectando...");
                        break; // Salir del switch

                    default:
                        // Opción inválida
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break; // Salir del switch
                } // Fin del switch
            } // Fin del while

        } catch (RemoteException e) {
            System.err.println("Error de RMI: No se pudo conectar o comunicar con el servidor.");
            System.err.println("Detalles: " + e.getMessage());
            // e.printStackTrace(); // Descomentar para ver el stack trace completo si es necesario
        } catch (NotBoundException e) {
            System.err.println("Error: El nombre 'server' no está registrado en el RMI Registry.");
            System.err.println("Asegúrese de que el servidor esté corriendo y registrado correctamente.");
            System.err.println("Detalles: " + e.getMessage());
        } catch (Exception e) { // Captura genérica para otros posibles errores
             System.err.println("Ocurrió un error inesperado en el cliente: " + e.getMessage());
             e.printStackTrace();
        } finally {
            scanner.close(); // Siempre cerrar el scanner al final
            System.out.println("Cliente terminado.");
        }
    } // Fin del main
} // Fin de la clase