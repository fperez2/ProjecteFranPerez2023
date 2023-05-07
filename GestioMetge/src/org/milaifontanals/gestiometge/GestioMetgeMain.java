package org.milaifontanals.gestiometge;

import javax.swing.SwingUtilities;
import org.milaifontanals.interficie.GestorCitesMediquesFactoria;
import org.milaifontanals.interficie.IGestorCitesMediques;

public class GestioMetgeMain {

    public static void main(String[] args) {    
        IGestorCitesMediques obj = null;
        try 
        {
            obj = GestorCitesMediquesFactoria.getInstance("org.milaifontanals.epjpa.EPJPA", "EPJPA.properties");
        } catch (Exception ex) {
            System.out.println("Problema en crear el component: "+ex.getMessage());
            System.out.println("Avortem aplicació");
            return;
        } 
        VistaMetge vm = new VistaMetge(obj);
        vm.mostrar();
    }
    
}
