// Patient.java
import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    private static final long serialVersionUID = 1L;

    private String patientId;
    private String medicalHistory;
    private String emergencyContact;
    private List<Appointment> appointments;
    private EHR healthRecord;

    public Patient() {
        super();
        this.appointments = new ArrayList<>();
    }

    public Patient(String userId, String name, String email, String password,
                   String phoneNumber, String patientId, String medicalHistory,
                   String emergencyContact) {
        super(userId, name, email, password, phoneNumber);
        this.patientId = patientId;
        this.medicalHistory = medicalHistory;
        this.emergencyContact = emergencyContact;
        this.appointments = new ArrayList<>();
        this.healthRecord = new EHR(patientId, name);
    }

    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public EHR getHealthRecord() { return healthRecord; }
    public void setHealthRecord(EHR healthRecord) { this.healthRecord = healthRecord; }

    @Override
    public String getRole() {
        return "Patient";
    }

    public void bookAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void updateProfile(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void viewAppointments() {
        for (Appointment apt : appointments) {
            System.out.println(apt);
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Patient ID: " + patientId +
                ", Medical History: " + medicalHistory;
    }
}