package com.calc.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateAndTime = dateFormat.format(new Date());
        
        String resultMessage = num1 + " " + operator + " " + num2 + " = " + result;

        try {
            // PostgreSQL JDBC sürücüsünü yüklemek için.
            Class.forName("org.postgresql.Driver");
            // Veritabanına bağlanmak için gerekli bilgileri tanımlıyoruz.
            String url = "jdbc:postgresql://localhost:5432/calculation_logs";
            String username = "postgres";
            String password = "9658";

            // Veritabanına bağlantı açıyoruz.
            Connection connection = DriverManager.getConnection(url, username, password);

            // Veritabanına log mesajını ve oluşturulma tarihini eklemek için bir sorgu hazırlıyoruz.
            String insertQuery = "INSERT INTO logs (log_message, created_at) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, resultMessage);
            // Şu anki zamanı alarak Timestamp nesnesine dönüştürüp sorguya ekliyoruz.
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            // Sorguyu veritabanına gönderiyoruz ve log kaydını ekliyoruz.
            preparedStatement.executeUpdate();

            // Kullanılan kaynakları kapatıyoruz.
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            // Eğer veritabanı işlemlerinde hata olursa, hatayı yazdırıyoruz.
            e.printStackTrace();
        }


        request.setAttribute("resultMessage", resultMessage);
        request.getRequestDispatcher("result.jsp").forward(request, response);
    }
}
