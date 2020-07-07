package edu.educacionit;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor<T> extends Thread {
    protected List<T> elementosPendeintes;

    public Actor() {
        elementosPendeintes = new ArrayList<T>();
        this.start();
    }
    public int obtenerCantMensajesEncolados() {
        int x = 0;
        synchronized(elementosPendeintes) {
            x = elementosPendeintes.size();
        }
        return x;
    }
    public void encolarMensaje(T t) {
        synchronized(elementosPendeintes) {
            elementosPendeintes.add(t);
            elementosPendeintes.notify();
        }
    }
    public void run() {
        System.out.println("Levantando actor... ");
        for (;;) {
            try {
                for (;;) {
                    T t = null;
                    synchronized(elementosPendeintes) {
                        elementosPendeintes.wait(100);
                        if (elementosPendeintes.size() > 0) {
                            t = elementosPendeintes.remove(0);
                        }
                    }
                    if (t != null) {
                        onNewMessage(t);
                    }
                }
            }
            catch (Exception ex) {
                System.out.println("Error en Actor");
                System.out.println(ex.getMessage());
            }
        }
    }
    protected abstract void onNewMessage(T t);
}
