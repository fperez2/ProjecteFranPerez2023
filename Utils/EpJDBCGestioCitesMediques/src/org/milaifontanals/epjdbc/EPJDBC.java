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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
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
                Persona p;
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
                if(cognom2 != null && !cognom2.isEmpty()){
                    p = new Persona(nif,nom,cognom1,cognom2,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);
                }else{
                    p = new Persona(nif,nom,cognom1,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);
                }  
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
       String query = "select c.*, p.Persona_Nom, p.Persona_Cognom1, e.Especialitat_Nom " +
                        "from cita c " +
                        "join metge m on m.Metge_CodiEmpleat = c.Cita_Metge_CodiEmpleat " +
                        "join persona p on m.Metge_Persona_NIF = p.Persona_NIF " +
                        "join entradahorari eh on m.Metge_CodiEmpleat = eh.Metge_CodiEmpleat " +
                        "and TIME(eh.EntradaHorari_Hora) = TIME(c.Cita_DataHora) " +
                        "and CASE " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 1 THEN eh.EntradaHorari_DiaSetmana = 'Diumenge' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 2 THEN eh.EntradaHorari_DiaSetmana = 'Dilluns' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 3 THEN eh.EntradaHorari_DiaSetmana = 'Dimarts' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 4 THEN eh.EntradaHorari_DiaSetmana = 'Dimecres' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 5 THEN eh.EntradaHorari_DiaSetmana = 'Dijous' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 6 THEN eh.EntradaHorari_DiaSetmana = 'Divendres' " +
                        "           WHEN DAYOFWEEK(c.Cita_DataHora) = 7 THEN eh.EntradaHorari_DiaSetmana = 'Dissabte' " +
                        "       END " +
                        "join especialitat e on e.Especialitat_Codi = eh.Especialitat_Codi " +
                        "where c.Cita_Persona_NIF = ? and c.Cita_DataHora >= CURDATE() " +
                        "ORDER BY c.Cita_DataHora ASC ";
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
                    Timestamp data = rs.getTimestamp("Cita_DataHora");
                    java.util.Date date = new java.util.Date(data.getTime());
                    String informe = rs.getString("Cita_Informe");
                    String nom_Metge = rs.getString("Persona_Nom");
                    String cognom_Metge = rs.getString("Persona_Cognom1");
                    String nom_Especialitat = rs.getString("Especialitat_Nom");
                    if(informe != null){
                        c = new Cita(codi_empleat, nif, date, informe, nom_Metge + cognom_Metge, nom_Especialitat);
                    }else{
                        c = new Cita(codi_empleat, nif, date, nom_Metge + cognom_Metge, nom_Especialitat);
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
        String query = "select p.* from Metge m "
                + "join Persona p on p.Persona_NIF = m.Metge_Persona_NIF";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            if(rs.next()==false)
            {
                System.out.println("No data"); 
                return null;           
            }
            else
            {
                do
                {
                    Persona p;
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
                    if(cognom2 != null && !cognom2.isEmpty()){
                        p = new Persona(nif,nom,cognom1,cognom2,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);
                    }else{
                        p = new Persona(nif,nom,cognom1,data,adreca,poblacio,sexe.toCharArray()[0],login,pwd);
                    }
                    metges.add(p);
                }while(rs.next());
                return metges;
            }
            
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

            if(rs.next()==false)
            {
                System.out.println("No data"); 
                return null;           
            }
            else
            {
                do
                {
                String nif = rs.getString("Metge_Persona_NIF");
                int codiMetge = rs.getInt("Metge_CodiEmpleat");
                Metge m = new Metge(nif,codiMetge);  
                metges.add(m);
                }while(rs.next());
                return metges;
        }
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

            if(rs.next()==false)
            {
                System.out.println("No data"); 
                return null;           
            }
            else
            {
                do
                {
                String nom = rs.getString("Especialitat_Nom");
                int codi = rs.getInt("Especialitat_Codi");
                Especialitat e = new Especialitat(codi,nom);  
                especialitats.add(e);
                }while(rs.next());
                return especialitats;
            }
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
    public List<EntradaHorari> getHorariMetges() {
        List<EntradaHorari> ehs = new ArrayList();
        String query = "select * from EntradaHorari";
        PreparedStatement st = null;
        ResultSet rs = null;
        try 
        {
                              
            st = con.prepareStatement(query);
            rs = st.executeQuery();

             if(rs.next()==false)
            {
                System.out.println("No data"); 
                return null;           
            }
            else
            {
                do
                {
                String diaSetmana = rs.getString("EntradaHorari_DiaSetmana");
                int codiMetge = rs.getInt("Metge_CodiEmpleat");
                int codiEsp = rs.getInt("Especialitat_Codi");
                Timestamp hora = rs.getTimestamp("EntradaHorari_Hora");
                EntradaHorari eh = new EntradaHorari(codiMetge,codiEsp,hora,diaSetmana);  
                ehs.add(eh);
                }while(rs.next());
                return ehs;
            }
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getHorariMetges: "+ex.getMessage());
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
    public void deleteCita(Cita cita) {
        String query = "delete from Cita where Cita_Metge_CodiEmpleat = ? and Cita_Persona_NIF = ? and Cita_DataHora = ?";
         
        PreparedStatement st = null;
     
        try
        {         
            st = con.prepareStatement(query);
            st.setInt(1, cita.getCodiMetge());
            st.setString(2, cita.getNif());
            java.util.Date utilDate = cita.getDataHora();
            
            Timestamp timestamp = new Timestamp(utilDate.getTime());
            st.setTimestamp(3, timestamp);
            
            st.executeUpdate();
            st.close();
            con.commit();
        } catch (SQLException ex) {
            boolean rollback = true;
            try {
                con.rollback();
            } catch (SQLException ex1) {
                rollback = false;
            }
            if (rollback == false) {
                throw new IGestorCitesMediquesException("Problemes en executar i no s'ha pogut efectuar rollback", ex);
            } else {
                throw new IGestorCitesMediquesException("Error en la execucio del metode deleteCita ", ex);
            }
        }
    }

    @Override
    public List<String> getForats(Date date, int codiMetge, int codiEsp) {
        List<String> ehs = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String query = "select * " +
                    "from entradahorari eh " +
                    "join metge m on m.Metge_CodiEmpleat = eh.Metge_CodiEmpleat " +
                    "where eh.Metge_CodiEmpleat = ? and eh.Especialitat_Codi = ? and " +
                    "CASE " +
                    "           WHEN DAYOFWEEK(?) = 1 THEN eh.EntradaHorari_DiaSetmana = 'Diumenge' " +
                    "           WHEN DAYOFWEEK(?) = 2 THEN eh.EntradaHorari_DiaSetmana = 'Dilluns' " +
                    "           WHEN DAYOFWEEK(?) = 3 THEN eh.EntradaHorari_DiaSetmana = 'Dimarts' " +
                    "           WHEN DAYOFWEEK(?) = 4 THEN eh.EntradaHorari_DiaSetmana = 'Dimecres' " +
                    "           WHEN DAYOFWEEK(?) = 5 THEN eh.EntradaHorari_DiaSetmana = 'Dijous' " +
                    "           WHEN DAYOFWEEK(?) = 6 THEN eh.EntradaHorari_DiaSetmana = 'Divendres' " +
                    "           WHEN DAYOFWEEK(?) = 7 THEN eh.EntradaHorari_DiaSetmana = 'Dissabte' " +
                    "       END " +
                    "and TIME(eh.EntradaHorari_Hora) not in ( " +
                    "    SELECT TIME(c.Cita_DataHora) " +
                    "    FROM cita c " +
                    "    WHERE DATE(c.Cita_DataHora) =  DATE(?) and c.Cita_Metge_CodiEmpleat = ? " +
                    ") ";
         
        PreparedStatement st = null;
        ResultSet rs = null;
        try
        {         
            st = con.prepareStatement(query);
            st.setInt(1, codiMetge);
            st.setInt(2, codiEsp);
            java.util.Date utilDate = date;
            Timestamp timestamp = new Timestamp(utilDate.getTime());
            st.setTimestamp(3, timestamp);
            st.setTimestamp(4, timestamp);
            st.setTimestamp(5, timestamp);
            st.setTimestamp(6, timestamp);
            st.setTimestamp(7, timestamp);
            st.setTimestamp(8, timestamp);
            st.setTimestamp(9, timestamp);
            st.setTimestamp(10, timestamp);
            st.setInt(11, codiMetge);
            rs = st.executeQuery();

             if(rs.next()==false)
            {
                System.out.println("No data"); 
                return null;           
            }
            else
            {
                do
                {
                Timestamp hora = rs.getTimestamp("EntradaHorari_Hora");
                ehs.add(sdf.format(hora));
                }while(rs.next());
                return ehs;
            }
        } catch (SQLException ex) {
            throw new IGestorCitesMediquesException("Error en el metode getForats: "+ex.getMessage());
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
    public void reservarCita(String nif, Date date, int codiMetge, String hora) {
        String query = "insert into Cita values (?,?,?, null)";
         
        PreparedStatement st = null;
     
        try
        {         
            st = con.prepareStatement(query);
            st.setInt(1, codiMetge);
            st.setString(2, nif);
            String dataHoraString = date.toString() + " " + hora;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date dataHora = dateFormat.parse(dataHoraString);
            
            Timestamp timestamp = new Timestamp(dataHora.getTime());
            st.setTimestamp(3, timestamp);
            
            st.executeUpdate();
            st.close();
            con.commit();
        } catch (SQLException ex) {
            boolean rollback = true;
            try {
                con.rollback();
            } catch (SQLException ex1) {
                rollback = false;
            }
            if (rollback == false) {
                throw new IGestorCitesMediquesException("Problemes en executar i no s'ha pogut efectuar rollback", ex);
            } else {
                throw new IGestorCitesMediquesException("Error en la execucio del metode deleteCita ", ex);
            }
        } catch (ParseException ex) {
            throw new IGestorCitesMediquesException("Error en parsear dates ", ex);
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
