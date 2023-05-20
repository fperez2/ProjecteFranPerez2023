package org.milaifontanals.servidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import org.milaifontanals.interficie.IGestorCitesMediques;


public class threadTractarClient extends Thread{
    protected Socket socket;
    private Hashtable<String,Integer> users = new Hashtable<>();
    IGestorCitesMediques igcm;
    public threadTractarClient(Socket clientSocket,IGestorCitesMediques igcm, Hashtable<String,Integer> users) {
        this.socket = clientSocket;
        this.igcm = igcm;
        this.users = users;
    }
    @Override
    public void run() {
        ObjectOutputStream toClient;
        ObjectInputStream fromClient;
        try {
            fromClient = new ObjectInputStream(socket.getInputStream());
            toClient = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }       
        String line ="";
      
        try 
        {
            line = (String)fromClient.readObject(); 
            System.out.println(line);
            if (line.equalsIgnoreCase("<<EXIT>>")) {
                String session_id = (String)fromClient.readObject();
                users.remove(session_id);
                socket.close();
            } 
        } catch (Exception e) {
            e.printStackTrace();          
        }  
    }  
}

