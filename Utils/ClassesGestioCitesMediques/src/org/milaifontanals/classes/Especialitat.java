package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Especialitat implements Serializable{    
    private int codi;
    private String nom;
    
    private List<MetgeEspecialitat> metges = new ArrayList();
    
    protected Especialitat(){}

    public Especialitat(int codi, String nom) {
        setCodi(codi);
        setNom(nom);
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        if(codi==0)
        {
            throw new RuntimeException("El codi es obligatori.");
        }
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if(nom==null)
        {
            throw new RuntimeException("El nom es obligatori.");
        }
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.codi;
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
        final Especialitat other = (Especialitat) obj;
        if (this.codi != other.codi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Especialitat{" + "codi=" + codi + ", nom=" + nom + '}';
    }
    
        public Iterator<MetgeEspecialitat> iteMetge()
    {
        return metges.iterator();
    }
        
    public boolean addMetge(MetgeEspecialitat u) {
        if (!metges.contains(u)) {
            metges.add(u);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeMetge(MetgeEspecialitat u)
    {
        if(metges.contains(u))
        {
            metges.remove(u);
            return true;
        }else{
            return false;
        }
    }
 
}
