package org.milaifontanals.gestiometge;

import javax.swing.SwingUtilities;

public class GestioMetgeMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VistaMetge doctorView = new VistaMetge();
                doctorView.mostrar();
            }
        });
    }
    
}
