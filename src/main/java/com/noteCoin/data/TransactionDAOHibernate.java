package com.noteCoin.data;

import com.noteCoin.data.dao.TransactionDAO;
import com.noteCoin.data.factories.ConnectionDataBase_Factory;
import com.noteCoin.models.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TransactionDAOHibernate implements TransactionDAO {

    public Boolean save(Transaction transaction) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();
        Boolean status;

        em.getTransaction().begin();
        try {
            em.persist(transaction);
            em.getTransaction().commit();
            status = true;
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = false;
        }finally {
            connection.closeConnect();
        }
        return status;
    }

    public List<Transaction> getList(String strQuery) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        try {
            Query objQuery = em.createQuery(strQuery);
            List list = objQuery.getResultList();
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
            connection.closeConnect();
        }
    }


    public List<Transaction> getLimitedList(String strQuery, Integer maxResults) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        try {
            Query objQuery = em.createQuery(strQuery);
            objQuery.setMaxResults(maxResults);
            List list = objQuery.getResultList();
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
            connection.closeConnect();
        }
    }

    public Boolean remove(Transaction transaction) {
        ConnectionDataBase_Factory connection = new ConnectionDataBase_Factory();
        EntityManager em = connection.getConnectionPostgreSQL();

        Boolean status;
        Long transactionId = transaction.getId();

        em.getTransaction().begin();
        try{
            Transaction tr = em.find(Transaction.class, transactionId);
            if (tr != null) {
                em.remove(tr);
                status = true;
            }else{
                status = false;
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            status = false;
        }finally {
            connection.closeConnect();
        }
        return status;
    }
}