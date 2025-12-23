// Appointment.java
import java.io.Serializable;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String appointmentId;
    private String patientId;
    private String providerId;
    private String dateTime;
    private String status; // Scheduled, Completed, Cancelled
    private String consultationType; // Video, Phone, In-person

    public Appointment() {}

    public Appointment(String appointmentId, String patientId, String providerId,
                       String dateTime, String consultationType) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.providerId = providerId;
        this.dateTime = dateTime;
        this.status = "Scheduled";
        this.consultationType = consultationType;
    }

    // Getters and Setters
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getConsultationType() { return consultationType; }
    public void setConsultationType(String consultationType) {
        this.consultationType = consultationType;
    }

    public void scheduleAppointment() {
        this.status = "Scheduled";
    }

    public void cancelAppointment() {
        this.status = "Cancelled";
    }

    public void rescheduleAppointment(String newDateTime) {
        this.dateTime = newDateTime;
        this.status = "Rescheduled";
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + ", Patient: " + patientId +
                ", Provider: " + providerId + ", Date/Time: " + dateTime +
                ", Status: " + status + ", Type: " + consultationType;
    }
}