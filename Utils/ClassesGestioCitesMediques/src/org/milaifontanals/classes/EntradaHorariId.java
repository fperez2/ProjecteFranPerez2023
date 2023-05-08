package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EntradaHorariId implements Serializable{    
    private int codiMetge;
    private Date hora;
    private String diaSetmana;

    
    protected EntradaHorariId(){}

    public EntradaHorariId(int codiMetge, Date hora, String diaSetmana) {
        this.codiMetge = codiMetge;
        this.hora = hora;
        this.diaSetmana = diaSetmana;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.codiMetge;
        hash = 47 * hash + Objects.hashCode(this.hora);
        hash = 47 * hash + Objects.hashCode(this.diaSetmana);
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
        final EntradaHorariId other = (EntradaHorariId) obj;
        if (this.codiMetge != other.codiMetge) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        if (this.diaSetmana != other.diaSetmana) {
            return false;
        }
        return true;
    }

    

    
}
