package org.milaifontanals.test;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.MetgeEspecialitat;
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
            
            Scanner sc = new Scanner(System.in);
            System.out.println("Introdueixi codi de metge:");
            int codi = 0;
            try {
                codi = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Valor erroni");
            }
            
            Metge metge = em.find(Metge.class, codi);
            if (metge == null) {
                System.out.println("No existeix metge amb codi " + codi);
            }
            System.out.println("Metge: " + metge);
            
            Persona persona = em.find(Persona.class, metge.getNif());
            if (persona == null) {
                System.out.println("No existeix persona amb codi " + codi);
            }
            System.out.println("Persona: " + persona);
            
            Query q = em.createQuery("select e from MetgeEspecialitat e");
            List<MetgeEspecialitat> ll = q.getResultList();
            if (ll.isEmpty()) {
                System.out.println("No té especialitats");
            } else {
                System.out.println("Especialitats:");
                System.out.println("**********");
                for (MetgeEspecialitat c : ll) {
                    if(c.getCodiEmpleat() == metge){
                    System.out.println("\t" + c.toString());
                    }
                }
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
