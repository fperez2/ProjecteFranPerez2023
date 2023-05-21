package org.milaifontanals.classes;

import java.io.Serializable;


public class Login implements Serializable{
    private String session_id;
    private Persona p;

    public Login(String session_id, Persona p) {
        this.session_id = session_id;
        this.p = p;
    }
    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public Persona getPersona() {
        return p;
    }

    public void setPersona(Persona p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Login{" + "session_id=" + session_id + ", persona=" + p + '}';
    }
}
