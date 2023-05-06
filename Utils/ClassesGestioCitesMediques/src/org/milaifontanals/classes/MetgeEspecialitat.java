package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Objects;

public class MetgeEspecialitat implements Serializable{
    private int codiMetge;
    private int codiEspecialitat;
    
    private Metge codiEmpleat;
    private Especialitat codi;

    
    protected MetgeEspecialitat(){}

    public MetgeEspecialitat(Metge codiEmpleat, Especialitat codi) {
        setCodiEmpleat(codiEmpleat);
        setCodi(codi);
    }

    public Metge getCodiEmpleat() {
        return codiEmpleat;
    }

    public void setCodiEmpleat(Metge codiEmpleat) {
        if(codiEmpleat==null)
        {
            throw new RuntimeException("El codi d'empleat es obligatori.");
        }
        this.codiEmpleat = codiEmpleat;
    }

    public Especialitat getCodi() {
        return codi;
    }

    public void setCodi(Especialitat codi) {
        if(codi==null)
        {
            throw new RuntimeException("El codi d'especialitat es obligatori.");
        }
        this.codi = codi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codiEmpleat);
        hash = 67 * hash + Objects.hashCode(this.codi);
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
        final MetgeEspecialitat other = (MetgeEspecialitat) obj;
        if (!Objects.equals(this.codiEmpleat, other.codiEmpleat)) {
            return false;
        }
        if (!Objects.equals(this.codi, other.codi)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MetgeEspecialitat{" + "codiEmpleat=" + codiEmpleat + ", codi=" + codi + '}';
    }
    
    
}
