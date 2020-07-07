package edu.educacionit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProcesaMensajeJavascript extends Actor<Socket> {
    protected void onNewMessage(Socket socket) {
        try {
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
            
            Numerador.incrementar();
            String mensaje = "Gracias por conectarse, su numero es: "
                    .concat(String.valueOf(Numerador.getNumero())) ;
            
            mensaje = "$('#numerador').html('" + mensaje +"');";
            
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
            System.out.println("Error en actor Procesar mensaje javascript");
            System.out.println(ex.getMessage());
        }
    }
    
}
