package pt.uminho.di.aa;

import org.orm.PersistentException;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        try {
            UserDAO.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            User user2 = UserDAO.getUserByORMID(7);
            System.out.println(user2.getID());
        } catch (PersistentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
