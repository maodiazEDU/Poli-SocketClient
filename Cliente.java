// Este programa es un cliente que se conecta a un servidor TCP en una IP y puerto espec'ificos.
// El cliente solicita al usuario que ingrese un numero de telefono y lo envia al servidor.
// El servidor procesa el numero y devuelve una respuesta que el cliente imprime en la consola.
// El cliente intenta conectarse al servidor dos veces en caso de que falle la primera vez.
// Importar las librerias necesarias
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "10.37.129.10"; // IP del servidor
        int puerto = 5000; // Puerto de escucha del servidor
        Scanner sc = new Scanner(System.in); // Crear objeto Scanner para leer la entrada del usuario
        Socket socket = null; // Inicializar el socket a null
        BufferedReader entrada = null;
        PrintWriter salida = null;

        try {
            socket = new Socket(host, puerto); // Crear socket del cliente
            System.out.println("Conectado al servidor " + host + " en el puerto " + puerto); // Mensaje de conexion exitosa

            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Crear flujo de entrada
            salida = new PrintWriter(socket.getOutputStream(), true); // Crear flujo de salida

            for (int i = 0; i < 2; i++) { // Realizar la consulta dos veces
                System.out.println("Ingrese el numero de telefono:  "); // Solicitar al usuario el numero de telefono
                String telefono = sc.nextLine(); // Leer el numero de telefono
                salida.println(telefono); // Enviar el numero de telefono al servidor

                // Leer la respuesta completa del servidor
                for (int j = 0; j < 4; j++) { // Esperar hasta 4 lineas de respuesta (si la persona existe)}
                    String linea = entrada.readLine();
                    if (linea != null) {
                        System.out.println(linea);
                    } else {
                        break; // Si el servidor cierra la conexion
                    }
                    if (j == 0 && linea != null && linea.contains("Persona duena de ese numero telefonico no existe")) {
                        break;
                    }
                }
                System.out.println(); // Agregar una linea en blanco entre respuestas
            }
            System.out.println("Cerrando conexion con el servidor...");

        } catch (Exception e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage()); // Imprimir la traza de la excepcion
        } finally {
            try {
                if (salida != null) {
                    salida.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
                if (socket != null) {
                    socket.close();
                }
                if (sc != null) {
                    sc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}    