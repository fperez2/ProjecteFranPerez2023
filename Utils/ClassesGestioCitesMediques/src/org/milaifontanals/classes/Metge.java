package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Metge implements Serializable{
    
    private String nif;   
    private Persona persona;
    private int codiEmpleat;
    
    private List<MetgeEspecialitat> especialitats = new ArrayList();
    
    protected Metge(){}

    public Metge(String nif, int codiEmpleat) {
        setNif(nif);
        setCodiEmpleat(codiEmpleat);
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        if(nif==null)
        {
            throw new RuntimeException("El nif es obligatori.");
        }
        this.nif = nif;
    }

    public int getCodiEmpleat() {
        return codiEmpleat;
    }

    public void setCodiEmpleat(int codiEmpleat) {
        if(codiEmpleat==0)
        {
            throw new RuntimeException("El codi d'empleat es obligatori.");
        }
        this.codiEmpleat = codiEmpleat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.nif);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Metge other = (Metge) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Metge{" + "nif=" + nif + ", codiEmpleat=" + codiEmpleat + '}';
    }  
    
    public Iterator<MetgeEspecialitat> iteMetgeEspecialitat()
    {
        return especialitats.iterator();
    } 
    public boolean addEspecialitat(MetgeEspecialitat e) {
        if (!especialitats.contains(e)) {
            especialitats.add(e);
            return true;
        } else {
            return false;
        }
    } 
    public boolean removeEspecialitat(MetgeEspecialitat e)
    {
        if(especialitats.contains(e))
        {
            especialitats.remove(e);
            return true;
        }else{
            return false;
        }
    }
   
}
