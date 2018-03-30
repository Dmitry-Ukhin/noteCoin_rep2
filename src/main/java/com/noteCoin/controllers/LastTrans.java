package com.noteCoin.controllers;

import com.google.gson.Gson;
import com.noteCoin.data.WorkWith_DB;
import com.noteCoin.data.WorkWith_HerokuPostgresQL;
import com.noteCoin.data.WorkWith_MySQL;
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

        String requestToDB = "FROM Transaction ORDER BY date desc";

        Gson gson = new Gson();
        String json;


        if (req.getRequestURL().toString().contains("heroku")){
            WorkWith_DB dataBase = new WorkWith_HerokuPostgresQL();
            List<Transaction> transactionList = dataBase.loadFromDB(requestToDB, amountEntity);

            Integer debugStatus = dataBase.getDebugStatus();
            String debugString = dataBase.getDebugString();

            if (transactionList != null) {
                for (Transaction tr : transactionList) {
                    json = gson.toJson(tr);
                    resp.getWriter().printf(json);
                }
//                resp.getWriter().println("debugStatus=" + debugStatus + "&&debugString=" + debugString);
            } else {
                resp.getWriter().println("Download is fail");
            }
        }else {
            WorkWith_DB dataBase = new WorkWith_MySQL();
            List<Transaction> transactionList = dataBase.loadFromDB(requestToDB, amountEntity);
            if (transactionList != null) {
                for (Transaction tr : transactionList) {
                    json = gson.toJson(tr);
                    resp.getWriter().printf(json);
                }
            } else {
                resp.getWriter().println("Download is fail");
            }
        }
    }
}
