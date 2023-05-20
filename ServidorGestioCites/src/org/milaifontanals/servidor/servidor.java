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

public class servidor {
    private static Hashtable <String,Integer> users = new Hashtable<>(); 
    public static void main(String[] args) {
        
        ServerSocket serverSocket = null;
        InetAddress addr = null;
        Socket socket = null;
        
                
        try 
        {
            addr = InetAddress.getByName("192.168.1.29");
            serverSocket = new ServerSocket(10000,50,addr);
        } catch (IOException e) {
            e.printStackTrace();
        }     
        try 
        {
            while (true) 
            {
                socket = serverSocket.accept();
                System.out.println("He rebut un client");
                new threadTractarClient(socket,getFactoria(),users).start();
             }
        } catch (IOException e) {
            throw new IGestorCitesMediquesException("problema aqui: "+e.getMessage());
  
        }finally
        {
            try 
            {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }           
    }
    
    private static IGestorCitesMediques getFactoria()
    {
        IGestorCitesMediques obj = null;
        try 
        {
            obj = GestorCitesMediquesFactoria.getInstance("org.milaifontanals.epjdbc.EPJDBC","EPJDBC.properties");
        } catch (Exception ex) {
            System.out.println("Problema en crear el component: "+ex.getMessage());
            System.out.println("Avortem aplicació");
            return null;
        } 
        return obj;
    }
    
    
}
