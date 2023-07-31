package com.calc.facade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CalculateFacade {
    private Connection connection;

    public CalculateFacade(Connection connection) {
        this.connection = connection;
    }

    public void logResult(double num1, double num2, String operator, double result) throws SQLException {
        String resultMessage = num1 + " " + operator + " " + num2 + " = " + result;


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
    }
}
