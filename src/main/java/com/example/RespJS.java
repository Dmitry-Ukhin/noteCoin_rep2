package com.examples;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RespJS extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{

		resp.getWriter().println("RESPONSE to JS");
	}
}
