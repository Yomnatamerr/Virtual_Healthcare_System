// Bill.java
import java.io.Serializable;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private String billId;
    private String patientId;
    private String appointmentId;
    private double amount;
    private String status; // Pending, Paid, Overdue
    private String dateGenerated;

    public Bill() {}

    public Bill(String billId, String patientId, String appointmentId, double amount) {
        this.billId = billId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.status = "Pending";
        this.dateGenerated = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDateGenerated() { return dateGenerated; }
    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    public void generateBill() {
        this.dateGenerated = java.time.LocalDateTime.now().toString();
        this.status = "Pending";
    }

    public void processBill() {
        this.status = "Paid";
    }

    @Override
    public String toString() {
        return "Bill ID: " + billId + ", Patient: " + patientId +
                ", Appointment: " + appointmentId + ", Amount: $" + amount +
                ", Status: " + status + ", Date: " + dateGenerated;
    }
}