package org.milaifontanals.test;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.MetgeEspecialitat;
import org.milaifontanals.classes.MetgeEspecialitatId;
import org.milaifontanals.classes.Persona;

public class provaConnexio {
    public static void main(String[] args) {
        String up = "UP-MySQL";
        EntityManager em = null;
        EntityManagerFactory emf = null;
        try 
        {
            System.out.println("Intent amb " + up);
            emf = Persistence.createEntityManagerFactory(up);
            System.out.println("EntityManagerFactory creada");
            em = emf.createEntityManager();
            System.out.println();
            System.out.println("EntityManager creat");
            
            //Proves interficie
            
//            //getAllMetges
//            List<Metge> metges;
//            try
//            {
//               String instruccio = "select e from Metge e";
//               Query q = em.createQuery(instruccio);
//               metges = q.getResultList();
//
//               if (metges.isEmpty()) {
//                    System.out.println("No hi ha metges");
//                } else {
//                    System.out.println("Metges:");
//                    System.out.println("**********");
//                    for (Metge metge : metges) {
//                        System.out.println("\t" + metge); 
//                    }
//                }
//            }catch(Exception ex){
//                System.out.println("Error getAllMetges "+ex.getMessage());
//            }    
//        
//            //getMetgesByEspecialitat
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Introdueixi nom de especialitat:");
//            String especialitat = "";
//            try {
//                especialitat = sc.nextLine();
//            } catch (NumberFormatException ex) {
//                System.out.println("Valor erroni");
//            }
//            
//            try
//            {
//                String instruccio = "select me.codiEmpleat from MetgeEspecialitat me where me.codi.nom = "
//                + ":especialitat";
//                Query q = em.createQuery(instruccio);
//                q.setParameter("especialitat", especialitat);
//                metges = q.getResultList();
//
//                if (metges.isEmpty()) {
//                    System.out.println("No hi ha metges");
//                } else {
//                    System.out.println("Metges:");
//                    System.out.println("**********");
//                    for (Metge metge : metges) {
//                        System.out.println("\t" + metge); 
//                    }
//                }
//            }catch(Exception ex){
//                System.out.println("Error getMetgesByEspecialitat "+ex.getMessage()); 
//            }  
//            
//            //getPersonaByMetge
//            System.out.println("Introdueixi codiMetge:");
//            int codiMetge = 0;
//            try {
//                codiMetge = Integer.parseInt(sc.nextLine());
//            } catch (NumberFormatException ex) {
//                System.out.println("Valor erroni");
//            }
//            Persona dadesMetge;
//            try
//            {
//                String instruccio = "select p from Persona p where p.nif = "
//                + "(select m.nif from Metge m where m.codiEmpleat = :codiMetge)";
//                Query q = em.createQuery(instruccio);
//                q.setParameter("codiMetge", codiMetge);
//                dadesMetge =(Persona) q.getResultList().get(0);
//
//                if (dadesMetge == null) {
//                    System.out.println("No hi ha dades de persona");
//                } else {
//                    System.out.println("Dades Metge:");
//                    System.out.println("**********");
//                    System.out.println("\t" + dadesMetge); 
//                }
//            }catch(Exception ex){
//                System.out.println("Error getPersonaByMetge "+ex.getMessage()); 
//            }    
//            
//            //getAllEspecialitats
//            List<Especialitat> especialitats;
//            try
//            {
//               String instruccio = "select e from Especialitat e";
//               Query q = em.createQuery(instruccio);
//               especialitats = q.getResultList();
//
//               if (especialitats.isEmpty()) {
//                    System.out.println("No hi ha especialitats");
//                } else {
//                    System.out.println("Especialitats:");
//                    System.out.println("**********");
//                    for (Especialitat esp : especialitats) {
//                        System.out.println("\t" + esp);
//                    }
//                }
//            }catch(Exception ex){
//                System.out.println("Error getAllEspecialitats "+ex.getMessage()); 
//            }
//            
//            //getEspecialitatByMetge
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Introdueixi codimetge:");
//            int codiMetge = 0;
//            try {
//                codiMetge = Integer.parseInt(sc.nextLine());
//            } catch (NumberFormatException ex) {
//                System.out.println("Valor erroni");
//            }
//            List<Especialitat> especialitatsMetge;
//            try
//            {
//                String instruccio = "select me.codi from MetgeEspecialitat me where me.codiMetge = "
//                + ":codiMetge";
//                Query q = em.createQuery(instruccio);
//                q.setParameter("codiMetge", codiMetge);
//                especialitatsMetge = q.getResultList();
//
//                if (especialitatsMetge.isEmpty()) {
//                    System.out.println("No hi ha especialitats");
//                } else {
//                    System.out.println("Especialitats:");
//                    System.out.println("**********");
//                    for (Especialitat esp : especialitatsMetge) {
//                        System.out.println("\t" + esp);
//                    }
//                }
//            }catch(Exception ex){
//                System.out.println("Error getEspecialitatByMetge "+ex.getMessage()); 
//            }    
//            
//            //addEspecialitatToMetge
//            Scanner sc = new Scanner(System.in);
//            System.out.println("Introdueixi codimetge:");
//            int codiMetge = 0;
//            String especialitat = "";
//            try {
//                codiMetge = Integer.parseInt(sc.nextLine());
//                System.out.println("Introdueixi especialitat:");
//                especialitat = sc.nextLine();
//            } catch (NumberFormatException ex) {
//                System.out.println("Valor erroni");
//            }
//            try
//            {   
//                String instruccio = "select e from Especialitat e where e.nom = :especialitat";
//                Query q = em.createQuery(instruccio);
//                q.setParameter("especialitat", especialitat);
//                Especialitat esp =(Especialitat) q.getResultList().get(0);
//
//                Metge metge = em.find(Metge.class, codiMetge);
//                MetgeEspecialitat me = new MetgeEspecialitat(metge, esp);
//                System.out.println("\t" + esp);
//                System.out.println("\t" + metge);
//                System.out.println("\t" + me);
//                metge.addEspecialitat(me);
//                esp.addMetge(me);
//                em.getTransaction().begin();
//                em.persist(me);
//                em.getTransaction().commit();  
//            }catch(Exception ex)
//            {
//                System.out.println("Error addEspecialitatToMetge: "+ex.getMessage()); 
//            }
//            
            //deleteEspecialitatToMetge
            Scanner sc = new Scanner(System.in);
            System.out.println("Introdueixi codimetge:");
            int codiMetge = 0;
            String especialitat = "";
            try {
                codiMetge = Integer.parseInt(sc.nextLine());
                System.out.println("Introdueixi especialitat:");
                especialitat = sc.nextLine();
            } catch (NumberFormatException ex) {
                System.out.println("Valor erroni");
            }
            try
            {
                String instruccio = "select e from Especialitat e where e.nom = :especialitat";
                Query q = em.createQuery(instruccio);
                q.setParameter("especialitat", especialitat);
                Especialitat esp =(Especialitat) q.getResultList().get(0);

                Metge metge = em.find(Metge.class, codiMetge);
                MetgeEspecialitatId id_me = new MetgeEspecialitatId(metge.getCodiEmpleat(), esp.getCodi());
                MetgeEspecialitat me = em.find(MetgeEspecialitat.class, id_me);
                
                //Eliminar entradahorari
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
            }catch(Exception ex)
            {
                System.out.println("Error deleteEspecialitatToMetge: "+ex.getMessage()); 
            }    
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
                System.out.println("EntityManager tancat");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory tancada");
            }
        }
    }
   
}
