/**
 * last change 18.03.18 18:10
 */
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
        type = type.toLowerCase();
        if (type.equals("all") || type.equals("undefined")){
            type = null;
        }
        date = req.getParameter("date");
        if (date.equals("undefined-undefined-undefined")){
            date = null;
        } else if(date.contains("undefined")){
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

        requestToDB += " ORDER BY date DESC";

        /*
        Get data from data base
         */
        Gson gson = new Gson();
        String json;

        if (req.getRequestURL().toString().contains("heroku")){
            WorkWith_DB dataBase = new WorkWith_HerokuPostgresQL();
            List<Transaction> transactionList = dataBase.loadFromDB(requestToDB);

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
            List<Transaction> transactionList = dataBase.loadFromDB(requestToDB);
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