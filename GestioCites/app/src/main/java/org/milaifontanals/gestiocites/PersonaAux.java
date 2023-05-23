package org.milaifontanals.gestiocites;

public class PersonaAux {
    private String nif;
    private String nom;
    private String cognom1;

    public PersonaAux(String nif, String nom, String cognom1) {
        this.setNif(nif);
        this.setNom(nom);
        this.setCognom1(cognom1);

    }
    public PersonaAux() {}


    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
            this.nif = nif;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
            this.nom = nom;
    }

    public String getCognom1() {
        return this.cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String toString() {
        String persona = this.nom + " " + cognom1;
        return persona;
    }
}

