import java.io.Serializable;
import java.time.LocalDate;

/**
 * Bill class
 * Manages billing for consultations and services
 */
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int billCounter = 7000;

    private String billId;
    private String patientId;
    private String appointmentId;
    private double amount;
    private LocalDate billDate;
    private String status; // UNPAID, PAID
    private String description;

    public Bill() {
    }

    public Bill(String patientId, String appointmentId, double amount, String description) {
        this.billId = generateBillId();
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.amount = amount;
        this.billDate = LocalDate.now();
        this.status = "UNPAID";
        this.description = description;
    }

    private static String generateBillId() {
        return "BILL" + (billCounter++);
    }

    public static void setBillCounter(int counter) {
        billCounter = counter;
    }

    // Getters and Setters
    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Bill ID: " + billId + ", Patient: " + patientId +
                ", Amount: $" + amount + ", Date: " + billDate +
                ", Status: " + status + ", Description: " + description;
    }
}