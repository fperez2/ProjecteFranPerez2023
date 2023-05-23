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
import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Login;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.interficie.IGestorCitesMediques;


public class threadTractarClient extends Thread{
    protected Socket socket;
    private Hashtable<String,String> users = new Hashtable<>();
    IGestorCitesMediques igcm;
    public threadTractarClient(Socket clientSocket,IGestorCitesMediques igcm, Hashtable<String,String> users) {
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
            }else if(line.equalsIgnoreCase("<<LOGIN>>"))
            {
                String login = (String)fromClient.readObject();
                String password = (String)fromClient.readObject();
                Persona p = igcm.login(login, password);

                if(p != null)
                {
                    String session_id = UUID.randomUUID().toString();
                    users.put(session_id,p.getNif());
                    System.out.println("Session_id: "+session_id+" NIF persona: "+p.getNif());
                    Login l = new Login(session_id,p);
                    toClient.writeObject(l);
                    toClient.flush();

                    System.out.println("OBJECTE ENVIAT");

                   // socket.close();
                }else
                {
                   //incorrecte
                    toClient.writeObject(null);
                    System.out.println("OBJECTE ENVIAT INCORRECTE");
                }
            }else if(line.equalsIgnoreCase("<<CITES>>"))
            {
                String session_id = (String)fromClient.readObject();

                System.out.println("Session id: "+session_id);
                String nif = users.get(session_id);
                System.out.println("User_id NIF: " + nif);
                if(nif==null)
                {
                    toClient.writeObject(null);
                    System.out.println("NULL");
                    toClient.flush();
                }else
                {
                    List<Cita> cites = igcm.getCites(nif);
                    toClient.writeObject(cites);
                    toClient.flush();
                    System.out.println("He enviat " + cites.size() + " cites");
                }

            }else if(line.equalsIgnoreCase("<<PERSONAMETGE>>"))
            {
                List<Persona> metges = igcm.getMetgeNames();
                toClient.writeObject(metges);
                toClient.flush();
                System.out.println("He enviat " + metges.size() + " persones que son metges");
            }else if(line.equalsIgnoreCase("<<METGES>>"))
            {
                List<Metge> metges = igcm.getAllMetges();
                toClient.writeObject(metges);
                toClient.flush();
                System.out.println("He enviat " + metges.size() + " metges");
            }else if(line.equalsIgnoreCase("<<ESPECIALITATS>>"))
            {
                List<Especialitat> especialitats = igcm.getAllEspecialitats();
                toClient.writeObject(especialitats);
                toClient.flush();
                System.out.println("He enviat " + especialitats.size() + " especialitats");
            }else if(line.equalsIgnoreCase("<<ENTRADAHORARI>>"))
            {
                List<EntradaHorari> ehs = igcm.getHorariMetges();
                toClient.writeObject(ehs);
                toClient.flush();
                System.out.println("He enviat " + ehs.size() + " entradaHorari");
            }else if(line.equalsIgnoreCase("<<DELETE_CITA>>"))
            {
                Cita c = (Cita)fromClient.readObject();
                igcm.deleteCita(c);

                System.out.println("He eliminat la cita correctament");
            }              
            
        } catch (Exception e) {
            e.printStackTrace();          
        }  
    }  
}

