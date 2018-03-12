/**
 * last change 12.03.18 20:12
 */
package com.noteCoin.data;

import com.noteCoin.models.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class WorkWithMySQL implements WorkWithDB{

    EntityManagerFactory emf;
    EntityManager em;

    public WorkWithMySQL() {
        emf = Persistence.createEntityManagerFactory("noteCoinDB");
        em = emf.createEntityManager();
    }

    /**
     * TODO: overload for upload list Transactions to DB
     * @param transaction
     */
    public void saveToDB(Transaction transaction) {
        em.getTransaction().begin();
        try {
            em.persist(transaction);//I save my model of Transaction to db
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
        }finally {
            em.close();
            emf.close();
        }
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
}
