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
import org.milaifontanals.classes.Cita;
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
    public Persona login(String user, String password) {
        String query = "select * from Persona where Persona_Login = ? and Persona_Password = ? ";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            st.setString(1,user);
            st.setString(2,password);
            rs = st.executeQuery();

            if(rs.next()==false)
            {
                System.out.println("El login ha sigut incorrecte");
                return null;           
            }
            else
            {
                String nif = rs.getString("Persona_NIF");
                String nom = rs.getString("Persona_Nom");
                String cognom1 = rs.getString("Persona_Cognom1");
                String cognom2 = rs.getString("Persona_Cognom2");
                Date data = rs.getDate("Persona_DataNaix");
                String adreca = rs.getString("Persona_Adreca");
                String poblacio = rs.getString("Persona_Poblacio");
                String sexe = rs.getString("Persona_Sexe");
                String login = rs.getString("Persona_Login");
                String pwd = rs.getString("Persona_Password");
                Persona p = new Persona(nif,nom,cognom1,cognom2,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);  
                System.out.println("Login correcte");
                return p;
            }
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode login: "+ex.getMessage());
        }finally {     
            try 
            {
                if(st!=null)
                {
                     st.close();
                      rs.close();
                }             
            } catch (SQLException ex) {
                throw new IGestorCitesMediquesException("Error en tancar les sentències utilitzada per executar la consulta.", ex);
            }
        }
    }
    
    @Override
    public List<Cita> getCites(String nif) {
       List<Cita> cites = new ArrayList();
       String query = "select * from Cita " +
                        "where Cita_Persona_NIF = ? and Cita_DataHora >= CURDATE() " +
                        "ORDER BY Cita_DataHora ASC ";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = con.prepareStatement(query);
            st.setString(1, nif);
            System.out.println("Persona: " + nif);
            rs = st.executeQuery();         
            if (rs.next()==false) {    
                System.out.println("No data"); 
                return null;
            }else
            {
                do
                {
                    Cita c;
                    int codi_empleat = rs.getInt("Cita_Metge_CodiEmpleat");   
                    Date data = rs.getDate("descripcio");
                    int projecte_id = rs.getInt("Cita_DataHora");
                    String informe = rs.getString("Cita_Informe");
                    if(informe != null){
                        c = new Cita(codi_empleat, nif, data, informe);
                    }else{
                        c = new Cita(codi_empleat, nif, data);
                    }
                    cites.add(c);              
                }while(rs.next());
                return cites;
            }     
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getCites: "+ex.getMessage());
        } finally {
            try 
            {
                if(st!=null)
                {
                                   
                   st.close();
                }
            } catch (SQLException ex) {
                throw new IGestorCitesMediquesException("Error en tancar les sentències utilitzada per executar la consulta.", ex);
            }
        }
    }

    @Override
    public List<Persona> getMetgeNames() {
        List<Persona> metges = new ArrayList();
        String query = "select p.* from Metge m"
                + "join Persona p on p.Persona_NIF = m.Metge_Persona_NIF";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            do
            {
            String nif = rs.getString("Persona_NIF");
            String nom = rs.getString("Persona_Nom");
            String cognom1 = rs.getString("Persona_Cognom1");
            String cognom2 = rs.getString("Persona_Cognom2");
            Date data = rs.getDate("Persona_DataNaix");
            String adreca = rs.getString("Persona_Adreca");
            String poblacio = rs.getString("Persona_Poblacio");
            String sexe = rs.getString("Persona_Sexe");
            String login = rs.getString("Persona_Login");
            String pwd = rs.getString("Persona_Password");
            Persona p = new Persona(nif,nom,cognom1,cognom2,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);  
            metges.add(p);
            }while(rs.next());
            return metges;
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getMetgeNames: "+ex.getMessage());
        }finally {     
            try 
            {
                if(st!=null)
                {
                     st.close();
                      rs.close();
                }             
            } catch (SQLException ex) {
                throw new IGestorCitesMediquesException("Error en tancar les sentències utilitzada per executar la consulta.", ex);
            }
        }
    }
    
    @Override
    public List<Metge> getAllMetges() {
        List<Metge> metges = new ArrayList();
        String query = "select * from Metge";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            do
            {
            String nif = rs.getString("Metge_Persona_NIF");
            int codiMetge = rs.getInt("Metge_CodiEmpleat");
            Metge m = new Metge(nif,codiMetge);  
            metges.add(m);
            }while(rs.next());
            return metges;
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getAllMetges: "+ex.getMessage());
        }finally {     
            try 
            {
                if(st!=null)
                {
                     st.close();
                      rs.close();
                }             
            } catch (SQLException ex) {
                throw new IGestorCitesMediquesException("Error en tancar les sentències utilitzada per executar la consulta.", ex);
            }
        }
    }
    
    @Override
    public List<Especialitat> getAllEspecialitats() {
        List<Especialitat> especialitats = new ArrayList();
        String query = "select * from Especialitat";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            do
            {
            String nom = rs.getString("Especialitat_Nom");
            int codi = rs.getInt("Especialitat_Codi");
            Especialitat e = new Especialitat(codi,nom);  
            especialitats.add(e);
            }while(rs.next());
            return especialitats;
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getAllEspecialitats: "+ex.getMessage());
        }finally {     
            try 
            {
                if(st!=null)
                {
                     st.close();
                      rs.close();
                }             
            } catch (SQLException ex) {
                throw new IGestorCitesMediquesException("Error en tancar les sentències utilitzada per executar la consulta.", ex);
            }
        }
    }
    
    @Override
    public Metge getMetge(int codiMetge) {
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
