package org.milaifontanals.classes;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Cita implements Serializable{

    private int codiMetge;
    private String nif;
    private Metge metge;
    private Persona persona;
    private Date dataHora;
    private String informe;
    
    private String nom_Metge;
    private String nom_Especialitat;
    
    protected Cita(){}

    public Cita(int codiMetge, String nif, Date dataHora, String informe) {
        setCodiMetge(codiMetge);
        setNif(nif);
        setDataHora(dataHora);
        setInforme(informe);
    }

    public Cita(int codiMetge, String nif, Date dataHora) {
        setCodiMetge(codiMetge);
        setNif(nif);
        setDataHora(dataHora);
    }
    
    public Cita(int codiMetge, String nif, Date dataHora, String informe, String nom_Metge, String nom_Especialitat) {
        setCodiMetge(codiMetge);
        setNif(nif);
        setDataHora(dataHora);
        setInforme(informe);
        setNomMetge(nom_Metge);
        setNomEspecialitat(nom_Especialitat);
    }

    public Cita(int codiMetge, String nif, Date dataHora, String nom_Metge, String nom_Especialitat) {
        setCodiMetge(codiMetge);
        setNif(nif);
        setDataHora(dataHora);
        setNomMetge(nom_Metge);
        setNomEspecialitat(nom_Especialitat);
    }

    public int getCodiMetge() {
        return codiMetge;
    }

    public void setCodiMetge(int codiMetge) {
        if(codiMetge < 1)
        {
            throw new RuntimeException("El codiMetge es obligatori.");
        }
        this.codiMetge = codiMetge;
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

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        if(dataHora==null)
        {
            throw new RuntimeException("La data i hora es obligatoria.");
        }
        this.dataHora = dataHora;
    }

    public String getInforme() {
        return informe;
    }

    public void setInforme(String informe) {
        this.informe = informe;
    }

    public String getNomMetge() {
        return nom_Metge;
    }

    public void setNomMetge(String nom_Metge) {
        this.nom_Metge = nom_Metge;
    }

    public String getNomEspecialitat() {
        return nom_Especialitat;
    }

    public void setNomEspecialitat(String nom_Especialitat) {
        this.nom_Especialitat = nom_Especialitat;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.codiMetge);
        hash = 73 * hash + Objects.hashCode(this.nif);
        hash = 73 * hash + Objects.hashCode(this.dataHora);
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
        final Cita other = (Cita) obj;
        if (!Objects.equals(this.codiMetge, other.codiMetge)) {
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

    @Override
    public String toString() {
        return "Cita{" + "codiMetge=" + codiMetge + ", nif=" + nif + ", dataHora=" + dataHora + ", informe=" + informe + '}';
    }
    
    
}
