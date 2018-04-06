/**
 * last changed 16.03.18 20:40
 */
package com.noteCoin.controllers;

import com.noteCoin.data.*;
import com.noteCoin.data.dao.TransactionDAO;
import com.noteCoin.data.factories.TransactionFactory;
import com.noteCoin.models.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveTransaction extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        List<Object> listArgs = new ArrayList<Object>();

        /*
        Create list arguments to Factory of Transactions
         */
        try {
            listArgs.add(req.getParameter("type"));
            listArgs.add(Integer.parseInt(req.getParameter("sum")));
            listArgs.add(new Date());
            listArgs.add(req.getParameter("description"));
        }catch (Exception ex){
            ex.getMessage();

        }
        /*
        Create transaction
         */
        TransactionFactory transactionFactory = new TransactionFactory();
        Transaction transaction = transactionFactory.createTransaction(listArgs);

        /*
        Send transaction to data base
         */
        TransactionDAO transactionDAO = new TransactionDAOHibernate();
        if (transactionDAO.save(transaction)){
            writer.println("success");
        }else{
            writer.println("Failed, try again");
        }
    }
}
