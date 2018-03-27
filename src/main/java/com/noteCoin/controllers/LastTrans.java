package com.noteCoin.controllers;

import com.google.gson.Gson;
import com.noteCoin.data.WorkWithDB;
import com.noteCoin.data.WorkWithMySQL;
import com.noteCoin.models.Transaction;

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

        String requestToDB = "SELECT * FROM Transaction ORDER BY date DESC LIMIT " + amountEntity;

        Gson gson = new Gson();
        String json;

        WorkWithDB dataBase = new WorkWithMySQL();
        List<Transaction> transactionList = dataBase.loadFromDB(requestToDB);
        if (transactionList != null) {
            for (Transaction tr : transactionList) {
                System.out.println(tr.toString());
                json = gson.toJson(tr);
                resp.getWriter().printf(json);
            }
        }else{
            resp.getWriter().println("Download is fail");
        }
    }
}
