import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Payment class
 * Tracks payment transactions for bills
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int paymentCounter = 9000;

    private String paymentId;
    private String billId;
    private String patientId;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // Credit Card, Debit Card, PayPal, Insurance
    private String transactionStatus; // SUCCESS, FAILED, PENDING

    public Payment() {
    }

    public Payment(String billId, String patientId, double amount, String paymentMethod) {
        this.paymentId = generatePaymentId();
        this.billId = billId;
        this.patientId = patientId;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.transactionStatus = "SUCCESS";
    }

    private static String generatePaymentId() {
        return "PAY" + (paymentCounter++);
    }

    public static void setPaymentCounter(int counter) {
        paymentCounter = counter;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId + ", Bill: " + billId +
                ", Amount: $" + amount + ", Method: " + paymentMethod +
                ", Date: " + paymentDate + ", Status: " + transactionStatus;
    }
}