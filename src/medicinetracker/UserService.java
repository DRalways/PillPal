package medicinetracker;

import java.sql.*;

public class UserService {
    
    // Check if user exists
    public static boolean userExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error checking user existence");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Add new user
    public static boolean addUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            pstmt.setString(2, password); // In real app, hash this!
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ User added: " + username);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error adding user");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Validate login credentials
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error validating login");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Get user ID
    public static int getUserId(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
}