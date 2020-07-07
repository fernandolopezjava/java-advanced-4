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
    public Integer numerador = 1;
    
    static ProcesaMensajeJavascript[] actoresProcesaMensaje  = new ProcesaMensajeJavascript[10];
    static int idx = 0;
    
    private static void aceptarConexiones(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();
            actoresProcesaMensaje[idx].encolarMensaje(socket);
            idx = (idx == 9) ? 0 : idx+1;
            System.out.println("Encolando en thread: " + idx);
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
        for (int x = 0; x < 10; x++) {
            actoresProcesaMensaje[x] = new ProcesaMensajeJavascript();
        }
        crearServerSocket();
    }
}
