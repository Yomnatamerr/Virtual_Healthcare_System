import java.io.Serializable;
import java.time.LocalDate;

/**
 * Prescription class
 * Manages electronic prescriptions issued by providers
 */
public class Prescription implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int prescriptionCounter = 6000;

    private String prescriptionId;
    private String patientId;
    private String providerId;
    private String medication;
    private String dosage;
    private String frequency;
    private LocalDate issueDate;
    private String instructions;

    public Prescription() {
    }

    public Prescription(String patientId, String providerId, String medication,
                        String dosage, String frequency, String instructions) {
        this.prescriptionId = generatePrescriptionId();
        this.patientId = patientId;
        this.providerId = providerId;
        this.medication = medication;
        this.dosage = dosage;
        this.frequency = frequency;
        this.issueDate = LocalDate.now();
        this.instructions = instructions;
    }

    private static String generatePrescriptionId() {
        return "RX" + (prescriptionCounter++);
    }

    public static void setPrescriptionCounter(int counter) {
        prescriptionCounter = counter;
    }

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Prescription ID: " + prescriptionId + ", Medication: " + medication +
                ", Dosage: " + dosage + ", Frequency: " + frequency +
                ", Issue Date: " + issueDate + ", Patient: " + patientId;
    }
}