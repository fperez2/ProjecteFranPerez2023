package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class CitaId implements Serializable{
    
    private int codiMetge;
    private String nif;
    private Date dataHora;
    
    protected CitaId(){}

    public CitaId(int codiMetge, String nif, Date dataHora) {
        this.codiMetge = codiMetge;
        this.nif = nif;
        this.dataHora = dataHora;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.codiMetge;
        hash = 47 * hash + Objects.hashCode(this.nif);
        hash = 47 * hash + Objects.hashCode(this.dataHora);
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
        final CitaId other = (CitaId) obj;
        if (this.codiMetge != other.codiMetge) {
            return false;
        }
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        if (!Objects.equals(this.dataHora, other.dataHora)) {
            return false;
        }
        return true;
    }
        
}
