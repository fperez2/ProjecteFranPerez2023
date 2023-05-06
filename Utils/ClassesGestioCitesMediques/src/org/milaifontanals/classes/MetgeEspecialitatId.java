package org.milaifontanals.classes;

import java.io.Serializable;

public class MetgeEspecialitatId implements Serializable{
    private int codiMetge;
    private int codiEspecialitat;
    
    protected MetgeEspecialitatId(){}

    public MetgeEspecialitatId(int codiMetge, int codiEspecialitat) {
        this.codiMetge = codiMetge;
        this.codiEspecialitat = codiEspecialitat;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.codiMetge;
        hash = 97 * hash + this.codiEspecialitat;
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
        final MetgeEspecialitatId other = (MetgeEspecialitatId) obj;
        if (this.codiMetge != other.codiMetge) {
            return false;
        }
        if (this.codiEspecialitat != other.codiEspecialitat) {
            return false;
        }
        return true;
    }

    
    
}
