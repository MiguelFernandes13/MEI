package com.example;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.Query;



public class App 
{
    public static void main( String[] args ) throws NoSuchAlgorithmException
    {
        GMSFacade gms = new GMSFacade();
        //Platform p1 = gms.registPlatform("Mega Drive", 1988, "A 16-bit home video game console", "Sega");
        //Platform p2 = gms.registPlatform("PC", 1977, "A personal computer (PC) is a multi-purpose electronic computer whose size, capabilities, and price make it feasible for individual use", "IBM");
        //Platform p3 = gms.registPlatform("Playstation 4", 2013, "The PlayStation 4 (abbreviated as PS4) is a home video game console developed by Sony Computer Entertainment", "Sony");
        //
        //Game g1 = gms.registGame("Sonic the Hedgehog", 1991, 10, "Sonic the Hedgehog is a platform video game developed by Sonic Team and published by Sega for the Sega Genesis console", p1);
        //Game g2 = gms.registGame("Dune", 1993, 65, "The Battle for Arrakis is a real-time strategy Dune video game developed by Westwood Studios.", p1);
        //Game g3 = gms.registGame("Half Life 3", 2018, 60, "Half-Life 3 is a first-person shooter video game developed and published by Valve Corporation.", p2);
        //Game g4 = gms.registGame("Age of Empires", 1997, 20, "Age of Empires is a series of historical real-time strategy video games, originally developed by Ensemble Studios and published by Microsoft Studios.", p2);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try{
            md.update("1234".getBytes("UTF-8"));     
        }catch(Exception e){
            e.printStackTrace();
        }
        User u1 = gms.registUser("Miguel", "miguel@email.com", md.digest().toString());
        
        gms.close();

    }
}
