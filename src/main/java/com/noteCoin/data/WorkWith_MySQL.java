/**
 * last change 20.03.18 01:52
 */
package com.noteCoin.data;

import com.noteCoin.models.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class WorkWith_MySQL implements WorkWith_DB {

    private Integer debugStatus;
    private String debugString;

    private EntityManagerFactory emf;

    private EntityManager em;
    public WorkWith_MySQL() {
        emf = Persistence.createEntityManagerFactory("noteCoin_MySQL");
        em = emf.createEntityManager();
    }

    public void reloadConnectWithDB() {
        emf = Persistence.createEntityManagerFactory("noteCoin_MySQL");
        em = emf.createEntityManager();
    }

    /**
     * TODO: overload for upload list Transactions to DB
     * @param transaction
     */
    public Integer saveToDB(Transaction transaction) {
        Integer status;

        em.getTransaction().begin();
        try {
            em.persist(transaction);//I save my model of Transaction to db
            em.getTransaction().commit();
            status = 1;
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = 0;
        }finally {
            em.close();
            emf.close();
        }
        return status;
    }

    public List<Transaction> loadFromDB(String requestToDB) {
        try {
            Query query = em.createQuery(requestToDB);
            List list = query.getResultList();
            List<Transaction> transactionList = new ArrayList<Transaction>();
            for (Object obj : list){
                Transaction tr = (Transaction)obj;
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

    public List<Transaction> loadFromDB(String requestToDB, Integer maxResults) {
        try {
            Query query = em.createQuery(requestToDB);
            query.setMaxResults(maxResults);
            List list = query.getResultList();
            List<Transaction> transactionList = new ArrayList<Transaction>();
            for (Object obj : list){
                Transaction tr = (Transaction)obj;
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

    public Integer removeTransaction(Transaction transaction) {
        Integer status;
        Long transactionId = transaction.getId();

        em.getTransaction().begin();
        try{
            Transaction tr = em.find(Transaction.class, transactionId);
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
