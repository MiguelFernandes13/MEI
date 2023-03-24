package pt.uminho.di.aa;

import org.orm.PersistentException;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        User u = new User();
        try {
            UserDAO.save(u);
        } catch (Exception e){
            e.printStackTrace();
        }
        //try {
        //    Platform p2 = PlatformDAO.getPlatformByORMID(1);
        //    System.out.println("Loaded p2 with id "+ p2.getID());
        //} catch (PersistentException e) {
        //    throw new RuntimeException(e);
        //}

    }

}
