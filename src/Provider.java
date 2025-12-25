import java.util.ArrayList;
import java.util.List;

/**
 * Provider class extending User
 * Represents healthcare providers (doctors, nurses, specialists)
 */
public class Provider extends User {
    private static final long serialVersionUID = 1L;
    private static int providerCounter = 2000;

    private String specialty;
    private String availability;
    private List<String> appointmentIds;

    public Provider() {
        super();
        this.appointmentIds = new ArrayList<>();
    }

    public Provider(String name, String email, String phone, String password,
                    String specialty, String availability) {
        super(generateProviderId(), name, email, phone, password);
        this.specialty = specialty;
        this.availability = availability;
        this.appointmentIds = new ArrayList<>();
    }

    private static String generateProviderId() {
        return "PR" + (providerCounter++);
    }

    public static void setProviderCounter(int counter) {
        providerCounter = counter;
    }

    @Override
    public String getRole() {
        return "PROVIDER";
    }

    // Getters and Setters
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
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

    @Override
    public String toString() {
        return super.toString() + ", Role: Provider, Specialty: " + specialty +
                ", Availability: " + availability;
    }
}