// Provider.java
import java.util.ArrayList;
import java.util.List;

public class Provider extends User {
    private static final long serialVersionUID = 1L;

    private String providerId;
    private String specialty;
    private String availability;
    private List<Appointment> appointments;

    public Provider() {
        super();
        this.appointments = new ArrayList<>();
    }

    public Provider(String userId, String name, String email, String password,
                    String phoneNumber, String providerId, String specialty,
                    String availability) {
        super(userId, name, email, password, phoneNumber);
        this.providerId = providerId;
        this.specialty = specialty;
        this.availability = availability;
        this.appointments = new ArrayList<>();
    }

    // Getters and Setters
    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public List<Appointment> getAppointments() { return appointments; }
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String getRole() {
        return "Provider";
    }

    public void manageSchedule(String availability) {
        this.availability = availability;
    }

    public void conductConsultation(Appointment appointment, String diagnosis,
                                    String treatment) {
        appointment.setStatus("Completed");
        // Update EHR would be done through the system
    }

    public void issuePrescription(EHR ehr, String medication, String dosage,
                                  String instructions) {
        Prescription prescription = new Prescription(
                "RX" + System.currentTimeMillis(),
                medication, dosage, instructions, providerId
        );
        ehr.addPrescription(prescription);
    }

    @Override
    public String toString() {
        return super.toString() + ", Provider ID: " + providerId +
                ", Specialty: " + specialty + ", Availability: " + availability;
    }
}