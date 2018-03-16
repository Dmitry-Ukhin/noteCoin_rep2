/**
 * last change 12.03.18 20:14
 */
package com.noteCoin.parser;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Parser extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws 
		ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		PrintWriter writer = resp.getWriter();
		//writer.println("<!DOCTYPE HTML>");
		//writer.println("<html><head><title>noteCoin</title><meta charset=\"utf-8\"></head>");

		//writer.println("<body><p>Query: " + req.getParameter("command_line") + "</p>");
		//writer.println("<p>Command: " + whatCommand(getListWordsFromQuery(req)) + "</p></body></html>");
		writer.println(whatCommand(getListWordsFromQuery(req)));
	}

	public String whatCommand(List<String> listWords){
	
		String keyWordsForExpenses = "buy";

		for(int i = 0; i < listWords.size(); i++){
			if(keyWordsForExpenses.contains(listWords.get(i))){
				return "expenses";
			}
		}
		return "I don't now what is it";

	}

	public List getListWordsFromQuery(HttpServletRequest req){

		String query = req.getQueryString();
		query = query.replace("command_line=", "");
		query = query.replace("+", " ");
		query += " ";

		List<String> listWords = new ArrayList();
		for(int begin=0, end=0; begin<query.length() ;){
			end = query.indexOf(" ", begin);
			listWords.add(query.substring(begin, end));
			begin = end + 1;
		}
		return listWords;
	}
}
