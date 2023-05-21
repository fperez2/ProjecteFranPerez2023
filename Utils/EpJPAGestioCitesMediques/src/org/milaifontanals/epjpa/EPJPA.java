package org.milaifontanals.epjpa;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.MetgeEspecialitat;
import org.milaifontanals.classes.MetgeEspecialitatId;
import org.milaifontanals.classes.Persona;
import org.milaifontanals.interficie.IGestorCitesMediques;
import org.milaifontanals.interficie.IGestorCitesMediquesException;


public class EPJPA implements IGestorCitesMediques{
    private EntityManager em;
    EntityManagerFactory emf = null;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public EPJPA()
    {
        this("EPJPA.properties");
    }
    
     public EPJPA(String nomFitxerPropietats)  {
        if (nomFitxerPropietats == null) {
            nomFitxerPropietats = "EPJPA.properties";
        }
        Properties props = new Properties();
        try {
            props.load(new FileReader(nomFitxerPropietats));
        } catch (FileNotFoundException ex) {
            throw new IGestorCitesMediquesException("No es troba fitxer de propietats", ex);
        } catch (IOException ex) {
            throw new IGestorCitesMediquesException("Error en carregar fitxer de propietats", ex);
        }

        String up = props.getProperty("up");
        if (up == null) {
            throw new IGestorCitesMediquesException("Fitxer de propietats no conté propietat obligatòria <up>");
        }
        props.remove(up);       
        try {
            emf = Persistence.createEntityManagerFactory(up, props);
            em = emf.createEntityManager();
        } catch (Exception ex) {
            if (emf != null) {
                emf.close();
            }
            throw new IGestorCitesMediquesException("Error en crear EntityManagerFactory o EntityManager", ex);
        }

    }
   
    @Override
    public List<Metge> getAllMetges() {
        List<Metge> metges;
        try
        {
           String instruccio = "select e from Metge e";
           Query q = em.createQuery(instruccio);
           metges = q.getResultList();
           
           return metges;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getAllMetges "+ex.getMessage()); 
        }    
    }

    @Override
    public List<Metge> getMetgesByEspecialitat(String especialitat) {
        List<Metge> metges;
        try
        {
            String instruccio = "select me.codiEmpleat from MetgeEspecialitat me where me.codi.nom = "
            + ":especialitat";
            Query q = em.createQuery(instruccio);
            q.setParameter("especialitat", especialitat);
            metges = q.getResultList();
            
            return metges;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getMetgesByEspecialitat "+ex.getMessage()); 
        }  
    }

    @Override
    public Persona getPersonaByMetge(int codiMetge) {
        Persona dadesMetge;
        try
        {
            String instruccio = "select p from Persona p where p.nif = "
            + "(select m.nif from Metge m where m.codiEmpleat = :codiMetge)";
            Query q = em.createQuery(instruccio);
            q.setParameter("codiMetge", codiMetge);
            dadesMetge =(Persona) q.getResultList().get(0);
            
            return dadesMetge;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getPersonaByMetge "+ex.getMessage()); 
        }    
    }

    @Override
    public List<Especialitat> getAllEspecialitats() {
        List<Especialitat> especialitats;
        try
        {
           String instruccio = "select e from Especialitat e";
           Query q = em.createQuery(instruccio);
           especialitats = q.getResultList();
           
           return especialitats;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getAllEspecialitats "+ex.getMessage()); 
        }    
    }

    @Override
    public List<Especialitat> getEspecialitatByMetge(int codiMetge) {
        List<Especialitat> especialitatsMetge;
        try
        {
            String instruccio = "select me.codi from MetgeEspecialitat me where me.codiMetge = "
            + ":codiMetge";
            Query q = em.createQuery(instruccio);
            q.setParameter("codiMetge", codiMetge);
            especialitatsMetge = q.getResultList();
            
            return especialitatsMetge;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getEspecialitatByMetge "+ex.getMessage()); 
        }    
    }

    @Override
    public boolean addEspecialitatToMetge(int codiMetge, String especialitat) {
        try
        {   
            String instruccio = "select e from Especialitat e where e.nom = :especialitat";
            Query q = em.createQuery(instruccio);
            q.setParameter("especialitat", especialitat);
            Especialitat esp =(Especialitat) q.getResultList().get(0);
            
            Metge metge = em.find(Metge.class, codiMetge);
            MetgeEspecialitat me = new MetgeEspecialitat(metge, esp);
            metge.addEspecialitat(me);
            esp.addMetge(me);
            em.getTransaction().begin();
            em.persist(me);
            em.getTransaction().commit();  
            return true;
        }catch(Exception ex)
        {
            throw new IGestorCitesMediquesException("Error addEspecialitatToMetge: "+ex.getMessage()); 
        }
    }

    @Override
    public boolean deleteEspecialitatToMetge(int codiMetge, String especialitat) {
        try
        {
            String instruccio = "select e from Especialitat e where e.nom = :especialitat";
            Query q = em.createQuery(instruccio);
            q.setParameter("especialitat", especialitat);
            Especialitat esp =(Especialitat) q.getResultList().get(0);
            
            Metge metge = em.find(Metge.class, codiMetge);
            MetgeEspecialitatId id_me = new MetgeEspecialitatId(metge.getCodiEmpleat(), esp.getCodi());
            MetgeEspecialitat me = em.find(MetgeEspecialitat.class, id_me);

            instruccio = "select e from EntradaHorari e where e.codiEmpleat.codiEmpleat = :metge and e.codi.codi = :especialitat";
            q = em.createQuery(instruccio);
            q.setParameter("metge", codiMetge);
            q.setParameter("especialitat", esp.getCodi());
            List<EntradaHorari> enthors = q.getResultList();
            em.getTransaction().begin();
            if(!enthors.isEmpty()){
            for (EntradaHorari enthor : enthors) {
                    em.remove(enthor);
                }
            }
            metge.removeEspecialitat(me);
            esp.removeMetge(me);

            em.remove(me);
            em.getTransaction().commit();
            return true;
        }catch(Exception ex)
        {
             throw new IGestorCitesMediquesException("Error deleteEspecialitatToMetge: "+ex.getMessage()); 
        }    
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
       if (em != null) {
            EntityManagerFactory emf = em.getEntityManagerFactory();
            try {
                em.close();
                em = null;
            } catch (Exception ex) {
                throw new IGestorCitesMediquesException("Problemes en tancar l'EntityManager", ex);
            }
            try {
                emf.close();
            } catch (Exception ex) {
                throw new IGestorCitesMediquesException("Problemes en tancar l'EntityManagerFactory", ex);
            }
        }
    }

    @Override
    public void closeTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Metge getMetge(int codiMetge) {
        try
        {
           Metge metge = em.find(Metge.class, codiMetge);
           
           return metge;
        }catch(Exception ex){
            throw new IGestorCitesMediquesException("Error getMetge "+ex.getMessage()); 
        }    
    }

    @Override
    public Persona login(String user, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
