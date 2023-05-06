package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EntradaHorari implements Serializable{    
    private Metge codiEmpleat;
    private Date hora;
    private DiesSetmana diaSetmana;
    private Especialitat codi;
    
    protected EntradaHorari(){}

    public EntradaHorari(Metge codiEmpleat, Date hora, DiesSetmana diaSetmana, Especialitat codi) {
        setCodiEmpleat(codiEmpleat);
        setHora(hora);
        setDiaSetmana(diaSetmana);
        setCodi(codi);
    }

    public Metge getCodiEmpleat() {
        return codiEmpleat;
    }

    public void setCodiEmpleat(Metge codiEmpleat) {
        if(codiEmpleat==null)
        {
            throw new RuntimeException("El codiEmpleat es obligatori.");
        }
        this.codiEmpleat = codiEmpleat;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        if(hora==null)
        {
            throw new RuntimeException("L'hora es obligatoria.");
        }
        this.hora = hora;
    }

    public DiesSetmana getDiaSetmana() {
        return diaSetmana;
    }

    public void setDiaSetmana(DiesSetmana diaSetmana) {
        if(diaSetmana==null)
        {
            throw new RuntimeException("El dia de la setmana es obligatori.");
        }
        this.diaSetmana = diaSetmana;
    }

    public Especialitat getCodi() {
        return codi;
    }

    public void setCodi(Especialitat codi) {
        this.codi = codi;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.codiEmpleat);
        hash = 31 * hash + Objects.hashCode(this.hora);
        hash = 31 * hash + Objects.hashCode(this.diaSetmana);
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
        final EntradaHorari other = (EntradaHorari) obj;
        if (!Objects.equals(this.codiEmpleat, other.codiEmpleat)) {
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

    @Override
    public String toString() {
        return "EntradaHorari{" + "codiEmpleat=" + codiEmpleat + ", hora=" + hora + ", diaSetmana=" + diaSetmana + ", codi=" + codi + '}';
    }

    
}
