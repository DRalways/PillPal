package medicinetracker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineService {
    
    // Add medicine to database
    public static boolean addMedicine(String username, String name, String dosage, 
                                     String frequency, String time) {
        String query = "INSERT INTO medicines (username, name, dosage, frequency, time, status) " +
                      "VALUES (?, ?, ?, ?, ?, 'Active')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            pstmt.setString(2, name);
            pstmt.setString(3, dosage);
            pstmt.setString(4, frequency);
            pstmt.setString(5, time);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Medicine added: " + name);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error adding medicine");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // BACKWARD COMPATIBLE: Old method signature (2 parameters)
    public static void addMedicine(String name, String dosage) {
        // This calls the new method with default values
        addMedicine("default_user", name, dosage, "Once daily", "8:00 AM");
    }
    
    // Get all medicines for a user
    public static List<Medicine> getMedicines(String username) {
        List<Medicine> medicines = new ArrayList<>();
        String query = "SELECT * FROM medicines WHERE username = ? AND status = 'Active' " +
                      "ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Medicine med = new Medicine(
                    rs.getString("name"),
                    rs.getString("dosage"),
                    rs.getString("frequency"),
                    rs.getString("time"),
                    rs.getString("status")
                );
                med.setId(rs.getInt("id"));
                medicines.add(med);
            }
            
            System.out.println("✅ Loaded " + medicines.size() + " medicines");
            
        } catch (SQLException e) {
            System.err.println("❌ Error loading medicines");
            e.printStackTrace();
        }
        
        return medicines;
    }
    
    // BACKWARD COMPATIBLE: Old method signature (no parameters)
    public static List<Medicine> getMedicines() {
        // Return empty list or default user's medicines
        return getMedicines("default_user");
    }
    
    // Update medicine status
    public static boolean updateMedicineStatus(int medicineId, String newStatus) {
        String query = "UPDATE medicines SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, medicineId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error updating medicine status");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Delete medicine
    public static boolean deleteMedicine(int medicineId) {
        String query = "DELETE FROM medicines WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, medicineId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Medicine deleted");
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error deleting medicine");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Log medicine taken to history
    public static boolean logMedicineTaken(String username, String medicineName, 
                                          String dosage, String status) {
        String query = "INSERT INTO medicine_history (username, medicine_name, dosage, status) " +
                      "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            pstmt.setString(2, medicineName);
            pstmt.setString(3, dosage);
            pstmt.setString(4, status);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("❌ Error logging medicine history");
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Get adherence statistics
    public static double getAdherenceRate(String username, int days) {
        String query = "SELECT " +
                      "COUNT(*) as total, " +
                      "SUM(CASE WHEN status = 'Taken' THEN 1 ELSE 0 END) as taken " +
                      "FROM medicine_history " +
                      "WHERE username = ? " +
                      "AND taken_at >= datetime('now', '-' || ? || ' days')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            pstmt.setInt(2, days);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int total = rs.getInt("total");
                int taken = rs.getInt("taken");
                
                if (total > 0) {
                    return (taken * 100.0) / total;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    // Get medicine count
    public static int getActiveMedicineCount(String username) {
        String query = "SELECT COUNT(*) FROM medicines WHERE username = ? AND status = 'Active'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}