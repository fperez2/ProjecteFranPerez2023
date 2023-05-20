package org.milaifontanals.epjdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.interficie.IGestorCitesMediques;
import org.milaifontanals.interficie.IGestorCitesMediquesException;


public class EPJDBC implements IGestorCitesMediques {
    private Connection con;
  
    
    
    public EPJDBC(){
        this("EPJDBC.properties");
    }
    
    public EPJDBC(String nomFitxerPropietats) {
        if (nomFitxerPropietats == null) {
            nomFitxerPropietats = "EPJDBC.properties";
        }
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(nomFitxerPropietats));
        } catch (IOException ex) {
            throw new IGestorCitesMediquesException("Error en llegir de fitxer de propietats", ex);
        }
        String url = p.getProperty("url");
        if (url == null || url.length() == 0) {
            throw new IGestorCitesMediquesException("Fitxer de propietats " + nomFitxerPropietats + " no inclou propietat \"url\"");
        }
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        String driver = p.getProperty("driver");   
        if (driver != null && driver.length() > 0) {
            try {
                Class.forName(driver).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                throw new IGestorCitesMediquesException("Problemes en carregar el driver ", ex);
            }
        }
        try {
            if (user != null && user.length() > 0) {
                con = DriverManager.getConnection(url, user, password);
            } else {
                con = DriverManager.getConnection(url);
            }
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Problemes en establir la connexió ");
        }
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Problemes en desactivar autocommit ");
        }
    }   

    @Override
    public Metge getMetge(int codiMetge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Metge> getAllMetges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Metge> getMetgesByEspecialitat(String especialitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Persona getPersonaByMetge(int codiMetge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Especialitat> getAllEspecialitats() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Especialitat> getEspecialitatByMetge(int codiMetge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addEspecialitatToMetge(int codiMetge, String especialitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteEspecialitatToMetge(int codiMetge, String especialitat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rollback() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
