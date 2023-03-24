package com.example;

import java.io.File;

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
                .addAnnotatedClass(Game.class);
            StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties()).build();

            SessionFactory sf = cfg.buildSessionFactory(sr);

            Session s = sf.openSession();
            s.setHibernateFlushMode(FlushMode.COMMIT);

            Transaction t = s.beginTransaction();

            Game g = new Game();
            g.setName("GTA");
            g.setYear(2010);
            g.setPrice(1000);
            g.setDescription("GTA is a game");
            s.persist(g);

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
