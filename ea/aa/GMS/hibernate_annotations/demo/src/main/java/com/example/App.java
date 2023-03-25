package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;



public class App 
{
    public static void main( String[] args )
    {
        try{
            Configuration cfg = new Configuration()
                .configure(new File("/home/miguel/uni/4ano/ea/aa/GMS/hibernate_annotations/demo/hibernate.cfg.xml"))
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(Platform.class);
            StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties()).build();

            SessionFactory sf = cfg.buildSessionFactory(sr);

            Session s = sf.openSession();
            s.setHibernateFlushMode(FlushMode.COMMIT);

            Transaction t = s.beginTransaction();

            //Platform p = new Platform();
            //p.setName("PS4");
            //p.setYear(2010);
            //p.setDescription("PS4 is a console");
            //p.setManufacturer("Sony");
            //s.persist(p);
            Platform p = s.get(Platform.class, 1);

            //Game g = new Game();
            //g.setName("GTA");
            //g.setYear(2010);
            //g.setPrice(1000);
            //g.setDescription("GTA is a game");
            //g.setPlatform(p);
            //s.persist(g);
            Game g = s.get(Game.class, 1);

            Game g2 = new Game();
            g2.setName("GTA2");
            g2.setYear(2010);
            g2.setPrice(1000);
            g2.setDescription("GTA is a game");
            g2.setPlatform(p);
            s.persist(g2);

            List<Game> games = new ArrayList<>();
            games.add(g);
            games.add(g2);

            User u = new User();
            u.setName("Miguel");
            u.setEmail("miguel@email.com");
            u.setPassword("1234");
            u.setGames(games);
            s.persist(u);



            try{
                t.commit();
            }catch(Exception e){
                t.rollback();
                e.printStackTrace();
                System.out.println("Unable to commit changes");
            }
            s.close();
            StandardServiceRegistryBuilder.destroy(sr);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to connect to database");

        }
    }
}
