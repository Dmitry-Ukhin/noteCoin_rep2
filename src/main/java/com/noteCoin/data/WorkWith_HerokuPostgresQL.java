package com.noteCoin.data;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkWith_HerokuPostgresQL implements WorkWith_DB{

    private Integer debugStatus = 100;
    private String debugString = "OK";
    private EntityManagerFactory emf;
    private EntityManager em;

    public WorkWith_HerokuPostgresQL(){
        emf = Persistence.createEntityManagerFactory("noteCoin_PostgresQL");
        em = emf.createEntityManager();
        if (null == emf){
            debugStatus = 200;
        }
    }

    public void reloadConnectWithDB() {
        if (!em.isOpen()) {
            emf = Persistence.createEntityManagerFactory("noteCoin_PostgresQL");
            em = emf.createEntityManager();
        }
    }

    public Integer saveToDB(com.noteCoin.models.Transaction transaction) {
        Integer status;

        if (null == transaction){
            debugStatus = 201;
        }

        em.getTransaction().begin();
        try {
            em.persist(transaction);
            //I save my model of Transaction to db
            em.getTransaction().commit();
            status = 1;
        }catch (Exception ex){
            debugString = ex.getMessage();
            em.getTransaction().rollback();
            status = 0;
        }finally {
            em.close();
            emf.close();
        }
        return status;
    }

    public List<com.noteCoin.models.Transaction> loadFromDB(String requestToDB) {
        try {
            javax.persistence.Query query = em.createQuery(requestToDB);
            List list = query.getResultList();
            if (null == list){
                debugStatus = 202;
                return null;
            }
            List<com.noteCoin.models.Transaction> transactionList = new ArrayList<com.noteCoin.models.Transaction>();
            for (Object obj : list){
                com.noteCoin.models.Transaction tr = (com.noteCoin.models.Transaction)obj;
                transactionList.add(tr);
            }
            return transactionList;
        }catch (Exception ex){
            debugString = ex.getMessage();
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
            emf.close();
        }
    }

    public List<com.noteCoin.models.Transaction> loadFromDB(String requestToDB, Integer maxResults) {
        try {
            javax.persistence.Query query = em.createQuery(requestToDB);
            query.setMaxResults(maxResults);
            List list = query.getResultList();
            List<com.noteCoin.models.Transaction> transactionList = new ArrayList<com.noteCoin.models.Transaction>();
            for (Object obj : list){
                com.noteCoin.models.Transaction tr = (com.noteCoin.models.Transaction)obj;
                transactionList.add(tr);
            }
            return transactionList;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }finally {
            em.close();
            emf.close();
        }
    }

    public Integer removeTransaction(com.noteCoin.models.Transaction transaction) {
        Integer status;
        Long transactionId = transaction.getId();

        em.getTransaction().begin();
        try{
            com.noteCoin.models.Transaction tr = em.find(com.noteCoin.models.Transaction.class, transactionId);
            if (tr != null) {
                em.remove(tr);
                status = 1;
            }else{
                status = 0;
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = 0;
        }finally {
            em.close();
            emf.close();
        }
        return status;
    }

    public Integer getDebugStatus() {
        return debugStatus;
    }

    public String getDebugString() {
        return debugString;
    }
}
