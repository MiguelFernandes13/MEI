package com.example;

import java.io.File;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class GMSFacade {
    private Session s;
    private Transaction t;

    public GMSFacade() {
        Configuration cfg = new Configuration()
                .configure(new File("/home/miguel/uni/4ano/ea/aa/GMS/hibernate_annotations/demo/hibernate.cfg.xml"))
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Game.class)
                .addAnnotatedClass(Platform.class)
                .addAnnotatedClass(Form.class);
            StandardServiceRegistry sr = new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties()).build();

            SessionFactory sf = cfg.buildSessionFactory(sr);

            this.s = sf.openSession();
            this.s.setHibernateFlushMode(FlushMode.COMMIT);
    }

    private void commit() {
        try{
            t.commit();
        }catch(Exception e){
            t.rollback();
            e.printStackTrace();
            System.out.println("Unable to commit changes");
        }
    }


    public User registUser(String name, String email, String password) {
        t = s.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        s.persist(user);
        commit();
        return user;
    }

    public Game registGame(String name, int year, int price, String description, Platform platform) {
        t = s.beginTransaction();
        Game game = new Game();
        game.setName(name);
        game.setYear(year);
        game.setPrice(price);
        game.setDescription(description);
        game.setPlatform(platform);
        s.persist(game);
        commit();
        return game;
    }

    public Platform registPlatform(String name, int year, String description, String manufacturer) {
        t = s.beginTransaction();
        Platform platform = new Platform();
        platform.setName(name);
        platform.setYear(year);
        platform.setDescription(description);
        platform.setManufacturer(manufacturer);
        s.persist(platform);
        commit();
        return platform;
    }

    public List<Game> getUserGames(String userName){
        Query q = s.createQuery("from User u where u.name = :userName")
                                    .setParameter("userName", userName);
        User user = (User) q.getSingleResult();
        List<Game> games = user.getGames();
        return games;

    }

    public List<Game> getAllGames(){
        Query q = s.createQuery("from Game");
        List<Game> games = q.getResultList();
        return games;
    }

    public Game getGame(String gameName){
        Query q = s.createQuery("from Game g where g.name = :gameName")
                    .setParameter("gameName", gameName);
        Game game = (Game) q.getSingleResult();
        return game;
    }

    public Platform getPlatform(String platformName){
        Query q = s.createQuery("from Platform p where p.name = :name")
                    .setParameter("name", platformName);
        Platform platform = (Platform) q.getSingleResult();
        return platform;
    }

    public Game deleteGame(String gameName){
        t = s.beginTransaction();
        Game game = getGame(gameName);
        s.remove(game);
        commit();
        return game;
    }

    public void close() {
        s.close();
    }
    
}
