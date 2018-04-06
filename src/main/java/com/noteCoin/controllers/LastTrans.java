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
import java.util.List;

public class LastTrans extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer amountEntity = Integer.parseInt(req.getParameter("size"));
        String query = "FROM Transaction ORDER BY date desc";

        TransactionDAO transactionDAO = new TransactionDAOHibernate();
        List<Transaction> limitedList = transactionDAO.getLimitedList(query, amountEntity);

        if (limitedList != null) {
            for (Transaction tr : limitedList) {
                resp.getWriter().printf(ToJSON.convert(tr));
            }
        } else {
            resp.getWriter().println("Download is fail");
        }

    }
}
