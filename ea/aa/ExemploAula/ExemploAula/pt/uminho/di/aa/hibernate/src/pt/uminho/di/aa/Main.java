package pt.uminho.di.aa;

import org.orm.PersistentException;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Platform p = new Platform();
        try {
            PlatformDAO.save(p);
        } catch (PersistentException e){
            throw new RuntimeException(e);
        }
        try {
            Platform p2 = PlatformDAO.getPlatformByORMID(1);
            System.out.println("Loaded p2 with id "+ p2.getID());
        } catch (PersistentException e) {
            throw new RuntimeException(e);
        }

    }

}
