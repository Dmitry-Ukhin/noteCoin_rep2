/**
 * last change 20.03.18 01:28
 */
package com.noteCoin.controllers;

import com.noteCoin.data.WorkWithDB;
import com.noteCoin.data.WorkWithMySQL;
import com.noteCoin.models.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemoveTransaction extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestToDB = "FROM Transaction AS Tr WHERE ";
        String type, date, descr;

        type = req.getParameter("type");
        date = getDate(req.getParameter("date"));
        descr = req.getParameter("description");

        requestToDB += "type LIKE \'" + type + "\' AND ";
        requestToDB += "date LIKE \'" + date + "\' AND ";
        requestToDB += "descr LIKE \'" + descr + "\'";

        WorkWithDB dataBase = new WorkWithMySQL();
        List<Transaction> transactions = dataBase.loadFromDB(requestToDB);
        dataBase.reloadConnectWithDB();
        Integer status = dataBase.removeTransaction(transactions.get(0));
        if (status == 1){
            resp.getWriter().println("success");
        }else{
            resp.getWriter().println("removing is fail");
        }
    }

    private String getDate(String date){
        String day, month, year, time;
        String newDate;
        Integer start = 0, end;

        end = date.indexOf(" ", start);
        month = date.substring(start, end);
        month = getMonth(month);
        start = end + 1;

        end = date.indexOf(",", start);
        day = date.substring(start, end);
        start = end + 2;

        end = date.indexOf(" ", start);
        year = date.substring(start, end);
        start = end + 1;

        end = date.indexOf(" ", start);
        time = date.substring(start, date.length());

        time = getTime(time);

        newDate = year + "-" + month + "-" + day + " " + time;

        return newDate;
    }

    private String getTime(String time){
        String hour, minutes, seconds;
        Integer startIndex = 0, endIndex;

        endIndex = time.indexOf(":", startIndex);
        hour = time.substring(startIndex, endIndex);
        startIndex = endIndex + 1;

        endIndex = time.indexOf(":", startIndex);
        minutes = time.substring(startIndex, endIndex);
        startIndex = endIndex + 1;

        endIndex = time.indexOf(" ", startIndex);
        seconds = time.substring(startIndex, endIndex);

        if(!time.contains("PM")) {
            hour = "0" + hour;
            return hour + ":" + minutes + ":" + seconds;
        }else{
            Integer intHour = Integer.parseInt(hour);
            switch (intHour){
                case 1:
                    hour = "13";
                    break;
                case 2:
                    hour = "14";
                    break;
                case 3:
                    hour = "15";
                    break;
                case 4:
                    hour = "16";
                    break;
                case 5:
                    hour = "17";
                    break;
                case 6:
                    hour = "18";
                    break;
                case 7:
                    hour = "19";
                    break;
                case 8:
                    hour = "20";
                    break;
                case 9:
                    hour = "21";
                    break;
                case 10:
                    hour = "22";
                    break;
                case 11:
                    hour = "23";
                    break;
                case 12:
                    hour = "00";
                    break;
            }
            return hour + ":" + minutes + ":" + seconds;
        }
    }

    private String getMonth(String wrongMonth){
        List<String> listWrongMonths = new ArrayList<String>();
        listWrongMonths.add("Jan");
        listWrongMonths.add("Feb");
        listWrongMonths.add("Mar");
        listWrongMonths.add("Apr");
        listWrongMonths.add("May");
        listWrongMonths.add("Jun");
        listWrongMonths.add("Jul");
        listWrongMonths.add("Aug");
        listWrongMonths.add("Sep");
        listWrongMonths.add("Oct");
        listWrongMonths.add("Nov");
        listWrongMonths.add("Dec");

        for (int i = 0; i < listWrongMonths.size(); i++){
            if (wrongMonth.contains(listWrongMonths.get(i))){
                switch (i){
                    case 0:
                        return "01";
                    case 1:
                        return "02";
                    case 2:
                        return "03";
                    case 3:
                        return "04";
                    case 4:
                        return "05";
                    case 5:
                        return "06";
                    case 6:
                        return "07";
                    case 7:
                        return "08";
                    case 8:
                        return "09";
                    case 9:
                        return "10";
                    case 10:
                        return "11";
                    case 11:
                        return "12";
                }
            }
        }
        return null;
    }
}
