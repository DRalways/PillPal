package medicinetracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    
    // Database file location
    private static final String DB_URL = "jdbc:sqlite:data/medicine_tracker.db";
    
    // Singleton connection
    private static Connection connection = null;
    
    // Get database connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                
                // Create connection
                connection = DriverManager.getConnection(DB_URL);
                
                System.out.println("✅ Database connected successfully!");
                
                // Create tables if they don't exist
                createTables();
            }
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite JDBC driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        }
        
        return connection;
    }
    
    // Create tables on first run
    private static void createTables() {
        try (Statement stmt = connection.createStatement()) {
            
            // Create Users table
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """;
            stmt.execute(createUsersTable);
            System.out.println("✅ Users table created/verified");
            
            // Create Medicines table
            String createMedicinesTable = """
                CREATE TABLE IF NOT EXISTS medicines (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    name TEXT NOT NULL,
                    dosage TEXT NOT NULL,
                    frequency TEXT,
                    time TEXT,
                    status TEXT DEFAULT 'Active',
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (username) REFERENCES users(username)
                )
            """;
            stmt.execute(createMedicinesTable);
            System.out.println("✅ Medicines table created/verified");
            
            // Create History table
            String createHistoryTable = """
                CREATE TABLE IF NOT EXISTS medicine_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    medicine_name TEXT NOT NULL,
                    dosage TEXT,
                    taken_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    status TEXT NOT NULL,
                    FOREIGN KEY (username) REFERENCES users(username)
                )
            """;
            stmt.execute(createHistoryTable);
            System.out.println("✅ History table created/verified");
            
        } catch (SQLException e) {
            System.err.println("❌ Error creating tables!");
            e.printStackTrace();
        }
    }
    
    // Close connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}