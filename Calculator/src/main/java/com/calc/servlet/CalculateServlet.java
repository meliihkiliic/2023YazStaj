package com.calc.servlet;

import com.calc.facade.CalculateFacade;
import com.calc.util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalculateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        //gelen veriler degiskenlere ataniyor.

        double num1 = Integer.parseInt(request.getParameter("num1"));
        double num2 = Integer.parseInt(request.getParameter("num2"));
        String operation = request.getParameter("operation");

        double result = 0;
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
                    // eger kullanici 0'a bolmek isterse hata mesaji
                    response.setContentType("text/html");
                    response.getWriter().println("<h2>Can't divide zero!</h2>");
                    return;
                }
                break;
            default:
                break;
        }
        
        String resultMessage = num1 + " " + operator + " " + num2 + " = " + result;


        try {
            // Veritabanı bağlantısını singleton tasarım deseni ile elde ediyoruz.
            Connection connection = DBConnection.getInstance().getConnection();
            // Facade deseni ile loglama işlemini gerçekleştiriyoruz.
            CalculateFacade calculateFacade = new CalculateFacade(connection);
            calculateFacade.logResult(num1, num2, operator, result);

        } catch (SQLException e) {
            // Eğer veritabanı işlemlerinde hata olursa, hatayı yazdırıyoruz.
            e.printStackTrace();
        }



        request.setAttribute("resultMessage", resultMessage);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
