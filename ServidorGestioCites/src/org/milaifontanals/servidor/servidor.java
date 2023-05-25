package org.milaifontanals.servidor;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.interficie.GestorCitesMediquesFactoria;
import org.milaifontanals.interficie.IGestorCitesMediques;
import org.milaifontanals.interficie.IGestorCitesMediquesException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

public class servidor extends JFrame {

    private JLabel estadoLabel;
    private ServerSocket serverSocket;
    private Hashtable<String, String> users = new Hashtable<>();
    private ServerWorker serverWorker;

    public servidor() {
        super("Servidor");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton encenderButton = new JButton("Encender");
        encenderButton.setBounds(50, 50, 100, 30);
        encenderButton.addActionListener(e -> {
            estadoLabel.setText("Encendido");
            iniciarServidor();
        });
        add(encenderButton);

        JButton apagarButton = new JButton("Apagar");
        apagarButton.setBounds(150, 50, 100, 30);
        apagarButton.addActionListener(e -> {
            estadoLabel.setText("Apagado");
            detenerServidor();
        });
        add(apagarButton);

        estadoLabel = new JLabel("Estado:");
        estadoLabel.setBounds(50, 100, 200, 30);
        add(estadoLabel);
    }

    private void iniciarServidor() {
        if (serverWorker == null || serverWorker.isDone()) {
            serverWorker = new ServerWorker();
            serverWorker.execute();
        }
    }

    private void detenerServidor() {
        if (serverWorker != null && !serverWorker.isDone()) {
            serverWorker.cancel(true);
        }
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() throws Exception {
            InetAddress addr = null;
            Socket socket = null;

            try {
                addr = InetAddress.getByName("10.200.1.21"); //10.200.1.21 //192.168.1.29 //192.168.81.60
                serverSocket = new ServerSocket(10000, 50, addr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (!isCancelled()) {
                    socket = serverSocket.accept();
                    System.out.println("He rebut un client");
                    new threadTractarClient(socket, getFactoria(), users).start();
                }
            } catch (IOException e) {
                throw new IGestorCitesMediquesException("problema aqui: " + e.getMessage());

            } finally {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null;
        }
    }

    private IGestorCitesMediques getFactoria() {
        IGestorCitesMediques obj = null;
        try {
            obj = GestorCitesMediquesFactoria.getInstance("org.milaifontanals.epjdbc.EPJDBC", "EPJDBC.properties");
        } catch (Exception ex) {
            System.out.println("Problema en crear el component: " + ex.getMessage());
            System.out.println("Avortem aplicació");
            return null;
        }
        return obj;
    }

    public static void main(String[] args) {
        servidor ventana = new servidor();
        ventana.setVisible(true);
    }
}
