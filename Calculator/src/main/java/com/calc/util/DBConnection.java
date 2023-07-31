package com.calc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Singleton nesnesi, static ve private olarak tanımlanır.
    private static DBConnection instance;

    // Veritabanı bağlantısını tutacak private değişken.
    private Connection connection;

    // Constructor özel olmalıdır.
    private DBConnection() {
        // Veritabanı bağlantısı burada oluşturuluyor.
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/calculation_logs";
            String username = "postgres";
            String password = "9658";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Singleton nesnesini döndüren static metot.
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Dışarıdan bağlantıyı elde etmek için get metodu.
    public Connection getConnection() {
        return connection;
    }
}
