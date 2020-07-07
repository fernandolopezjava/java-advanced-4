package edu.educacionit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Init {
    private static void aceptarConexiones(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();
            // derivar a un actor y que ese actor se encargue
            // NO SE PUEDE HACER NADA SINCRONICO !!!!
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader br = new BufferedReader(isr);
            
            for (boolean corte = false; !corte;) {
                String linea = br.readLine();
                if (linea.getBytes().length == 0) {
                    corte = true;
                    continue;
                }
                System.out.println(linea);
            }
            
            System.out.println("Haciendo tarea pesada");
            try { Thread.sleep(10000); } catch (Exception ex) {}
            System.out.println("Fin haciendo tarea pesada");
            
            String mensaje = "Gracias por conectarse";
            
            
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: application/javascript; charset=utf-8");
            out.println("Server: Servidorsito nuestro");
            out.println("Content-Length: " + mensaje.length());
            out.println("");
            out.println(mensaje);
            out.flush();
            
            isr.close();
            out.close();
            socket.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private static void crearServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(12000);
            while (true) {
                aceptarConexiones(serverSocket);
            }
        }
        catch (Exception ex) {
            System.out.println("Error al intentar crear server socket");
        }
    }
    public static void main(String[] args) {
        crearServerSocket();
    }
}
