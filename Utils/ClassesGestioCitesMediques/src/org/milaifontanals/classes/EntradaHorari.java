package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class EntradaHorari implements Serializable{
    private int codiMetge;
    private int codiEspecialitat;
    
    private Metge codiEmpleat;
    private Date hora;
    private String diaSetmana;
    private Especialitat codi;
    
    protected EntradaHorari(){}

    public EntradaHorari(int codiMetge, int codiEspecialitat, Date hora, String diaSetmana) {
        this.codiMetge = codiMetge;
        this.codiEspecialitat = codiEspecialitat;
        this.hora = hora;
        this.diaSetmana = diaSetmana;
    }

    public int getCodiMetge() {
        return codiMetge;
    }

    public void setCodiMetge(int codiMetge) {
        this.codiMetge = codiMetge;
    }

    public int getCodiEspecialitat() {
        return codiEspecialitat;
    }

    public void setCodiEspecialitat(int codiEspecialitat) {
        this.codiEspecialitat = codiEspecialitat;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getDiaSetmana() {
        return diaSetmana;
    }

    public void setDiaSetmana(String diaSetmana) {
        this.diaSetmana = diaSetmana;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.codiMetge;
        hash = 79 * hash + this.codiEspecialitat;
        hash = 79 * hash + Objects.hashCode(this.hora);
        hash = 79 * hash + Objects.hashCode(this.diaSetmana);
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
        if (this.codiMetge != other.codiMetge) {
            return false;
        }
        if (this.codiEspecialitat != other.codiEspecialitat) {
            return false;
        }
        if (!Objects.equals(this.diaSetmana, other.diaSetmana)) {
            return false;
        }
        if (!Objects.equals(this.hora, other.hora)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EntradaHorari{" + "codiMetge=" + codiMetge + ", codiEspecialitat=" + codiEspecialitat + ", hora=" + hora + ", diaSetmana=" + diaSetmana + '}';
    }

}
