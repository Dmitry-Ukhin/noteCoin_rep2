/**
 * last change 17.03.18 14:56
 */
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
import java.io.PrintWriter;
import java.util.List;

public class ShowTransactions extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer;
        String type,
               date,
               descr;
        Boolean haveType=false,
                haveDate=false,
                haveDescr=false;
        String requestToDB = "FROM Transaction AS Tr WHERE ";

        /*
        Get arguments from request
         */
        type = req.getParameter("type");
        if (type.equals("all")){
            type = null;
        }
        date = req.getParameter("date");
        descr = req.getParameter("description");
        /*
        We have arguments?
         */
        if (type != null){
            haveType = true;
        }
        if (date != null){
            haveDate = true;
        }
        if (descr != null){
            haveDescr = true;
        }

        /*
        Build request to DataBase
         */
        if (haveType){
            requestToDB += "Tr.type LIKE \'" +type+ "\' AND ";
        }
        if (haveDate){
            requestToDB += "Tr.date LIKE \'" +date+ "%\' AND ";
        }
        if (haveDescr){
            requestToDB += "Tr.description LIKE \'" +descr+ "%\'";
        }
        int startIndex = 0;
        int lastIndex = requestToDB.lastIndexOf("AND");
        requestToDB = requestToDB.substring(startIndex, lastIndex);

        /*
        Get data from data base
         */
        Gson gson = new Gson();
        String json;

        WorkWithDB dataBase = new WorkWithMySQL();
        List<Transaction> transactionList = dataBase.loadFromDB(requestToDB);
        if (transactionList != null) {
            for (Transaction tr : transactionList) {
                json = gson.toJson(tr);
                resp.getWriter().println(json);
            }
        }else{
            resp.getWriter().println("Download is fail");
        }



    }
}
