package org.milaifontanals.interficie;

import java.sql.Date;
import java.util.List;
import org.milaifontanals.classes.Cita;
import org.milaifontanals.classes.EntradaHorari;
import org.milaifontanals.classes.Especialitat;
import org.milaifontanals.classes.Metge;
import org.milaifontanals.classes.Persona;

public interface IGestorCitesMediques {
    
    //Metodes Gestio Metges
    Metge getMetge(int codiMetge);
    List<Metge> getAllMetges();
    List<Metge> getMetgesByEspecialitat(String especialitat);
    Persona getPersonaByMetge(int codiMetge);
    List<Especialitat> getAllEspecialitats();
    List<Especialitat> getEspecialitatByMetge(int codiMetge);
    boolean addEspecialitatToMetge(int codiMetge, String especialitat);
    boolean deleteEspecialitatToMetge(int codiMetge, String especialitat);
    
    //Metodes Gestio Cites
    Persona login(String user, String password);
    List<Cita> getCites(String nif); 
    List<Persona> getMetgeNames();
    //List<Metge> getAllMetges();
    //List<Especialitat> getAllEspecialitats();
    List<EntradaHorari> getHorariMetges();
    void deleteCita(Cita cita);
    List<String> getForats(Date date, int codiMetge, int codiEsp);
    void reservarCita(String nif, Date date, int codiMetge, String hora);
    
    void commit();
    void rollback();
    void close();
    void closeTransaction();
    
}
