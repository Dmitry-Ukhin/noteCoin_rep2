package com.noteCoin.data.factories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionDataBase_Factory {
    private EntityManagerFactory emf;

    public synchronized EntityManager getConnectionPostgreSQL(){
        try {
            emf = Persistence.createEntityManagerFactory("noteCoin_PostgresQL");
            return emf.createEntityManager();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public synchronized EntityManager getConnectionMySQL(){
        try {
            emf = Persistence.createEntityManagerFactory("noteCoin_MySQL");
            return emf.createEntityManager();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;

    }

    public void closeConnect(){
        emf.close();
    }
}
