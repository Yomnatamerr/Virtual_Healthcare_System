// DataManager.java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String PATIENTS_FILE = "patients.dat";
    private static final String PROVIDERS_FILE = "providers.dat";
    private static final String APPOINTMENTS_FILE = "appointments.dat";
    private static final String BILLS_FILE = "bills.dat";
    private static final String FEEDBACK_FILE = "feedback.dat";

    // Save patients to file
    public static void savePatients(List<Patient> patients) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PATIENTS_FILE))) {
            oos.writeObject(patients);
        }
    }

    // Load patients from file
    @SuppressWarnings("unchecked")
    public static List<Patient> loadPatients() throws IOException, ClassNotFoundException {
        File file = new File(PATIENTS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(PATIENTS_FILE))) {
            return (List<Patient>) ois.readObject();
        }
    }

    // Save providers to file
    public static void saveProviders(List<Provider> providers) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(PROVIDERS_FILE))) {
            oos.writeObject(providers);
        }
    }

    // Load providers from file
    @SuppressWarnings("unchecked")
    public static List<Provider> loadProviders() throws IOException, ClassNotFoundException {
        File file = new File(PROVIDERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(PROVIDERS_FILE))) {
            return (List<Provider>) ois.readObject();
        }
    }

    // Save appointments to file
    public static void saveAppointments(List<Appointment> appointments) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(APPOINTMENTS_FILE))) {
            oos.writeObject(appointments);
        }
    }

    // Load appointments from file
    @SuppressWarnings("unchecked")
    public static List<Appointment> loadAppointments() throws IOException, ClassNotFoundException {
        File file = new File(APPOINTMENTS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(APPOINTMENTS_FILE))) {
            return (List<Appointment>) ois.readObject();
        }
    }

    // Save bills to file
    public static void saveBills(List<Bill> bills) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(BILLS_FILE))) {
            oos.writeObject(bills);
        }
    }

    // Load bills from file
    @SuppressWarnings("unchecked")
    public static List<Bill> loadBills() throws IOException, ClassNotFoundException {
        File file = new File(BILLS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(BILLS_FILE))) {
            return (List<Bill>) ois.readObject();
        }
    }

    // Save feedback to file
    public static void saveFeedback(List<Feedback> feedbackList) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(FEEDBACK_FILE))) {
            oos.writeObject(feedbackList);
        }
    }

    // Load feedback from file
    @SuppressWarnings("unchecked")
    public static List<Feedback> loadFeedback() throws IOException, ClassNotFoundException {
        File file = new File(FEEDBACK_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(FEEDBACK_FILE))) {
            return (List<Feedback>) ois.readObject();
        }
    }

    // Search patient by ID
    public static Patient searchPatientById(List<Patient> patients, String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    // Search provider by ID
    public static Provider searchProviderById(List<Provider> providers, String providerId) {
        for (Provider provider : providers) {
            if (provider.getProviderId().equals(providerId)) {
                return provider;
            }
        }
        return null;
    }

    // Search appointment by ID
    public static Appointment searchAppointmentById(List<Appointment> appointments,
                                                    String appointmentId) {
        for (Appointment apt : appointments) {
            if (apt.getAppointmentId().equals(appointmentId)) {
                return apt;
            }
        }
        return null;
    }
}