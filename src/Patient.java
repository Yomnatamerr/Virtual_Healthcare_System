import java.util.ArrayList;
import java.util.List;

/**
 * Patient class extending User
 * Manages patient-specific data and medical history
 */
public class Patient extends User {
    private static final long serialVersionUID = 1L;
    private static int patientCounter = 1000;

    private String medicalHistory;
    private String emergencyContact;
    private List<String> appointmentIds;
    private String ehrId;

    public Patient() {
        super();
        this.appointmentIds = new ArrayList<>();
    }

    public Patient(String name, String email, String phone, String password,
                   String medicalHistory, String emergencyContact) {
        super(generatePatientId(), name, email, phone, password);
        this.medicalHistory = medicalHistory;
        this.emergencyContact = emergencyContact;
        this.appointmentIds = new ArrayList<>();
        this.ehrId = "EHR-" + this.userId;
    }

    private static String generatePatientId() {
        return "P" + (patientCounter++);
    }

    public static void setPatientCounter(int counter) {
        patientCounter = counter;
    }

    @Override
    public String getRole() {
        return "PATIENT";
    }

    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public List<String> getAppointmentIds() {
        return appointmentIds;
    }

    public void setAppointmentIds(List<String> appointmentIds) {
        this.appointmentIds = appointmentIds;
    }

    public void addAppointment(String appointmentId) {
        this.appointmentIds.add(appointmentId);
    }

    public String getEhrId() {
        return ehrId;
    }

    public void setEhrId(String ehrId) {
        this.ehrId = ehrId;
    }

    @Override
    public String toString() {
        return super.toString() + ", Role: Patient, Medical History: " + medicalHistory;
    }
}