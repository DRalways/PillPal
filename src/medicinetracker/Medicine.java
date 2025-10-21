package medicinetracker;

/**
 * Simple model class for Medicine
 * Make sure this file is saved as src/medicinetracker/Medicine.java
 */
public class Medicine {
    private String name;
    private String dosage;
    private String frequency;
    private String time;
    private String status;

    public Medicine(String name, String dosage, String frequency, String time, String status) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.time = time;
        this.status = status;
    }

    // Minimal convenience constructor (keeps backward compatibility)
    public Medicine(String name, String dosage) {
        this(name, dosage, "", "", "Pending");
    }

    // Getters used by the views
    public String getName() { return name; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    // Setters if you need to update objects in-memory later
    public void setName(String name) { this.name = name; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public void setTime(String time) { this.time = time; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return name + " — " + dosage;
    }
}