package edu.educacionit;

public class Numerador {
    public static Integer numero;
    public static String Numerador = "SYNC"; 
    static {
        numero = 0;
    }
    public static Integer getNumero() {
        Integer varLocal;
        synchronized (Numerador) {
            varLocal = numero;
        }
        return varLocal;
    }
    public static void incrementar() {
        synchronized (Numerador) {
            numero++;
        }
    }
}
