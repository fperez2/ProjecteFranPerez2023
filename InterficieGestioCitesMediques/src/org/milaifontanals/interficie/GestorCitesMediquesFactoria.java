package org.milaifontanals.interficie;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GestorCitesMediquesFactoria {
    
    protected GestorCitesMediquesFactoria() {
    }
    
     static public IGestorCitesMediques getInstance(String nomClasseComponent,
            String parametrePelConstructorDelComponent) throws RuntimeException {
        IGestorCitesMediques obj = null;
        try {
            Class c = Class.forName(nomClasseComponent);
            if (parametrePelConstructorDelComponent==null ||
                    parametrePelConstructorDelComponent.length()==0) {
                obj = (IGestorCitesMediques) c.newInstance();
            } else {
                Constructor co = c.getConstructor(String.class);
                obj = (IGestorCitesMediques) co.newInstance(parametrePelConstructorDelComponent);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("Error " + nomClasseComponent + ex.getMessage(), ex);
        }
        return obj;
    }
}
