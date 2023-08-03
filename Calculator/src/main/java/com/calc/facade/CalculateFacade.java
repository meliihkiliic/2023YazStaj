package com.calc.facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CalculateFacade {
    private static final Logger logger = LogManager.getLogger(CalculateFacade.class);
    private Connection connection;

    public CalculateFacade(Connection connection) {
        this.connection = connection;
    }

    public void logResult(double num1, double num2, String operator, double result) throws SQLException {
    	//logResult methoduna giriş logunu tutuyoruz.
    	logger.debug("Entering logResult method.");
    	
        String resultMessage = num1 + " " + operator + " " + num2 + " = " + result;

        PreparedStatement preparedStatement = null; // Declare the variable here

        try {
            // Veritabanına log mesajını ve oluşturulma tarihini eklemek için bir sorgu hazırlıyoruz.
            String insertQuery = "INSERT INTO logs (log_message, created_at) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, resultMessage);
            // Şu anki zamanı alarak Timestamp nesnesine dönüştürüp sorguya ekliyoruz.
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            // Sorguyu veritabanına gönderiyoruz ve log kaydını ekliyoruz.
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // error logunu tutuyoruz.
            logger.error("Error occurred while logging result to the database.", e);
        } finally {
            if (preparedStatement != null) {
                try {
                    // Kullanılan kaynakları kapatıyoruz.
                    preparedStatement.close();
                } catch (SQLException e) {
                    // error logunu tutuyoruz.
                    logger.error("Error occurred while closing the prepared statement.", e);
                }
            }
        }

        // Methoddan çıkış logu 
        logger.debug("Exiting logResult method.");
    }
}

