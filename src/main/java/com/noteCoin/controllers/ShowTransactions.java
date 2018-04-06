/**
 * last change 18.03.18 18:10
 */
package com.noteCoin.controllers;

import com.noteCoin.data.dao.TransactionDAO;
import com.noteCoin.data.TransactionDAOHibernate;
import com.noteCoin.models.Transaction;
import com.noteCoin.tools.ToJSON;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTransactions extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type,
               date,
               descr;

        /*
        Get arguments from request
         */
        type = req.getParameter("type");
        type = type.toLowerCase();
        if (type.equals("all") || type.equals("undefined")){
            type = "%";
        }
        date = req.getParameter("date");
        if (date.equals("undefined-undefined-undefined")){
            date = "%-%-%";
        } else if(date.contains("undefined")){
            date = date.replaceAll("undefinded", "%");
        }
        descr = req.getParameter("description");
        if (descr.equals("")){
            descr = "%";
        }

        Map<String, String> args = new HashMap<String, String>();
        args.put("type", type);
        args.put("date", date);
        args.put("descr", descr);


        String query = "From Transaction ";
        query = query.concat("WHERE ");
        for (String key : args.keySet()) {
            query = query.concat(key + " LIKE \'" + args.get(key) + "%\' AND ");
        }
        Integer lastIndexOf = query.lastIndexOf("AND");
        if (lastIndexOf < query.length() - 3) {
            query = query.substring(0, lastIndexOf);
        }
        query = query.concat("ORDER BY date DESC");

        /*
        Get data from data base
         */

        TransactionDAO transactionDAO = new TransactionDAOHibernate();
        List<Transaction> transactionList = transactionDAO.getList(query);

        if (transactionList != null) {
            for (Transaction tr : transactionList) {
                resp.getWriter().printf(ToJSON.convert(tr));
            }
        } else {
            resp.getWriter().println("Download is fail");
        }
    }
}