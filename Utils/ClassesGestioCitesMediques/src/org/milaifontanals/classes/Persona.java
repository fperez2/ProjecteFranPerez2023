package org.milaifontanals.classes;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Persona implements Serializable{    
    private String nif;
    private String nom;
    private String cognom1;
    private String cognom2;
    private Date dataNaix;
    private String adreca;
    private String poblacio;
    private char sexe;
    private String login;
    private String password;
    
    private List<Cita> cites = new ArrayList();
    
    protected Persona(){}

    public Persona(String nif, String nom, String cognom1, String cognom2, Date dataNaix, String adreca, String poblacio, char sexe, String login, String password) {
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setCognom2(cognom2);
        setDataNaix(dataNaix);
        setAdreca(adreca);
        setPoblacio(poblacio);
        setSexe(sexe);
        setLogin(login);
        setPassword(password);
    }
    
        public Persona(String nif, String nom, String cognom1, Date dataNaix, String adreca, String poblacio, char sexe, String login, String password) {
        setNif(nif);
        setNom(nom);
        setCognom1(cognom1);
        setDataNaix(dataNaix);
        setAdreca(adreca);
        setPoblacio(poblacio);
        setSexe(sexe);
        setLogin(login);
        setPassword(password);
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
         if (nif.matches("\\d{8}[A-Z]")) {
            this.nif = nif;
        } else {
            throw new RuntimeException("El NIF ha de tenir 8 números seguits d'una lletra majúscula.");
        }
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

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        if(cognom1==null)
        {
            throw new RuntimeException("El primer cognom es obligatori.");
        }
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public Date getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(Date dataNaix) {
        if(dataNaix==null || dataNaix.after(new Date()))
        {
            throw new RuntimeException("La data de naixement es obligatoria i no pot ser futura.");
        }
        this.dataNaix = dataNaix;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        if(adreca==null)
        {
            throw new RuntimeException("L'adreça es obligatoria.");
        }
        this.adreca = adreca;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        if(poblacio==null)
        {
            throw new RuntimeException("La població es obligatoria.");
        }
        this.poblacio = poblacio;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        if(sexe == '\u0000')
        {
            throw new RuntimeException("El sexe es obligatori.");
        }
        this.sexe = sexe;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if(login==null)
        {
            throw new RuntimeException("El login es obligatori.");
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password==null)
        {
            throw new RuntimeException("La password es obligatoria.");
        }
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.nif);
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
        final Persona other = (Persona) obj;
        if (!Objects.equals(this.nif, other.nif)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String persona = "";
        persona += "NIF: " + nif + "\n";
        persona += "Nom: " + nom + "\n";
        persona += "Primer Cognom: " + cognom1 + "\n";
        if(cognom2 != null){
        persona += "Segon Cognom: " + cognom2 + "\n";
        } 
        persona += "Data Naixement: " + dataNaix + "\n";
        persona += "Adreça: " + adreca + "\n";
        persona += "Població: " + poblacio + "\n";
        if(sexe=='M'){
        persona += "Sexe: Home\n";
        }else{
            persona += "Sexe: Dona\n";
        }
        
        return persona;
    }
    
    public Boolean esMetge(){
        return null;
    }
    
    public Iterator<Cita> iteCites()
    {
        return Collections.unmodifiableCollection(cites).iterator();
    } 
    public boolean addCita(Cita e) {
        if (!cites.contains(e)) {
            cites.add(e);
            return true;
        } else {
            return false;
        }
    } 
    public boolean removeCita(Cita e)
    {
        if(cites.contains(e))
        {
            cites.remove(e);
            return true;
        }else{
            return false;
        }
    }
}
