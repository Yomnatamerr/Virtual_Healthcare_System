// Payment.java
import java.io.Serializable;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String paymentId;
    private String billId;
    private double amount;
    private String paymentMethod; // Credit Card, Debit Card, PayPal, Insurance
    private String paymentDate;
    private String status; // Success, Failed, Pending

    public Payment() {}

    public Payment(String paymentId, String billId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = "Pending";
        this.paymentDate = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getBillId() { return billId; }
    public void setBillId(String billId) { this.billId = billId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void processPayment() {
        this.status = "Success";
        this.paymentDate = java.time.LocalDateTime.now().toString();
    }

    public void refundPayment() {
        this.status = "Refunded";
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId + ", Bill: " + billId +
                ", Amount: $" + amount + ", Method: " + paymentMethod +
                ", Status: " + status + ", Date: " + paymentDate;
    }
}