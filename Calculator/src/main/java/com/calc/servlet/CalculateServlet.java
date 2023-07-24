package com.calc.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//gelen veriler degiskenlere ataniyor.
    	
        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        String operation = request.getParameter("operation");

        int result = 0;
        String operator = "";

        // gelen operator bilgisine gore islemler yapiliyor
        
        switch (operation) {
            case "add":
                result = num1 + num2;
                operator = "+";
                break;
            case "subtract":
                result = num1 - num2;
                operator = "-";
                break;
            case "multiply":
                result = num1 * num2;
                operator = "*";
                break;
            case "divide":
                if (num2 != 0) {
                    result = num1 / num2;
                    operator = "/";
                } else {
                	//eger kullanici 0'a bolmek isterse hata mesaji
                    response.setContentType("text/html");
                    response.getWriter().println("<h2>Can't divide zero!</h2>");
                    return;
                }
                break;
            default:
                break;
        }

        //sonuc resultMessage olarak kaydedilip result sayfasina gonderiliyor.
        String resultMessage = num1 + " " + operator + " " + num2 + " = " + result;
        request.setAttribute("resultMessage", resultMessage);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
