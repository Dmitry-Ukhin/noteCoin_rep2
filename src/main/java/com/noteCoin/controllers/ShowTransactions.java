/**
 * last change 18.03.18 18:10
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
        if (type.equals("all") || type.equals("undefined")){
            type = null;
        }
        date = req.getParameter("date");
        if (date.equals("none-none-none")){
            date = null;
        } else if(date.contains("none")){
            date = date.replaceAll("none", "%");
        }
        descr = req.getParameter("description");
        if (descr.equals("")){
            descr = null;
        }
        /*
        We have arguments?
         */
        Integer deleteWhere = 0;
        if (type != null){
            haveType = true;
        }else{
            deleteWhere--;
        }
        if (date != null){
            haveDate = true;
        }else{
            deleteWhere--;
        }
        if (descr != null){
            haveDescr = true;
        }else{
            deleteWhere--;
        }


        /*
        Build request to DataBase
         */
        if (haveType == true){
            requestToDB += "Tr.type LIKE \'" +type+ "%\' AND ";
        }
        if (haveDate == true){
            requestToDB += "Tr.date LIKE \'" +date+ "%\' AND ";
            if (requestToDB.contains("%%")){
                requestToDB = requestToDB.replaceAll("%%", "%");
            }
        }
        if (haveDescr == true){
            requestToDB += "Tr.descr LIKE \'" +descr+ "%\'";
        }else{
            if (requestToDB.contains("AND")) {
                int startIndex = 0;
                int lastIndex = requestToDB.lastIndexOf("AND");
                requestToDB = requestToDB.substring(startIndex, lastIndex);
            }
        }

        if (deleteWhere == -3){
            int startIndex = 0;
            int lastIndex = requestToDB.lastIndexOf("WHERE");
            requestToDB = requestToDB.substring(startIndex, lastIndex);
        }

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
                resp.getWriter().printf(json);
            }
        }else{
            resp.getWriter().println("Download is fail");
        }
    }
}