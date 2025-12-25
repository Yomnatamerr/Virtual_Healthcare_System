import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment class
 * Manages virtual appointment scheduling between patients and providers
 */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int appointmentCounter = 5000;

    private String appointmentId;
    private String patientId;
    private String providerId;
    private LocalDateTime dateTime;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    private String consultationType;
    private String notes;

    public Appointment() {
    }

    public Appointment(String patientId, String providerId, LocalDateTime dateTime,
                       String consultationType) {
        this.appointmentId = generateAppointmentId();
        this.patientId = patientId;
        this.providerId = providerId;
        this.dateTime = dateTime;
        this.consultationType = consultationType;
        this.status = "SCHEDULED";
        this.notes = "";
    }

    private static String generateAppointmentId() {
        return "APT" + (appointmentCounter++);
    }

    public static void setAppointmentCounter(int counter) {
        appointmentCounter = counter;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsultationType() {
        return consultationType;
    }

    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Appointment ID: " + appointmentId + ", Patient: " + patientId +
                ", Provider: " + providerId + ", Date/Time: " + dateTime.format(formatter) +
                ", Status: " + status + ", Type: " + consultationType;
    }
}