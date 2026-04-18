package medicinetracker;

public class Medicine {
    private int id;  // Database ID
    private String name;
    private String dosage;
    private String frequency;
    private String time;
    private String status;
    
    // Full Constructor
    public Medicine(String name, String dosage, String frequency, 
                    String time, String status) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.time = time;
        this.status = status;
    }
    
    // Simplified Constructor (for backward compatibility)
    public Medicine(String name, String dosage) {
        this(name, dosage, "Once daily", "8:00 AM", "Pending");
    }
    
    // Getters and Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDosage() { 
        return dosage; 
    }
    
    public void setDosage(String dosage) { 
        this.dosage = dosage; 
    }
    
    public String getFrequency() { 
        return frequency; 
    }
    
    public void setFrequency(String frequency) { 
        this.frequency = frequency; 
    }
    
    public String getTime() { 
        return time; 
    }
    
    public void setTime(String time) { 
        this.time = time; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    @Override
    public String toString() {
        return name + " — " + dosage;
    }
}