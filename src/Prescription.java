// Prescription.java
import java.io.Serializable;

public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;

    private String prescriptionId;
    private String medication;
    private String dosage;
    private String instructions;
    private String providerId;
    private String dateIssued;

    public Prescription() {}

    public Prescription(String prescriptionId, String medication, String dosage,
                        String instructions, String providerId) {
        this.prescriptionId = prescriptionId;
        this.medication = medication;
        this.dosage = dosage;
        this.instructions = instructions;
        this.providerId = providerId;
        this.dateIssued = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getDateIssued() { return dateIssued; }
    public void setDateIssued(String dateIssued) { this.dateIssued = dateIssued; }

    public void issuePrescription() {
        this.dateIssued = java.time.LocalDateTime.now().toString();
    }

    public void renewPrescription() {
        this.dateIssued = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return "Prescription ID: " + prescriptionId + ", Medication: " + medication +
                ", Dosage: " + dosage + ", Instructions: " + instructions +
                ", Issued by: " + providerId + ", Date: " + dateIssued;
    }
}