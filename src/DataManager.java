import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DataManager class
 * Handles persistence using file I/O with proper exception handling
 * Uses extensible collections for data management
 */
public class DataManager {
    private static final String DATA_DIR = "data/";
    private static final String PATIENTS_FILE = DATA_DIR + "patients.dat";
    private static final String PROVIDERS_FILE = DATA_DIR + "providers.dat";
    private static final String ADMINS_FILE = DATA_DIR + "administrators.dat";
    private static final String APPOINTMENTS_FILE = DATA_DIR + "appointments.dat";
    private static final String EHRS_FILE = DATA_DIR + "ehrs.dat";
    private static final String PRESCRIPTIONS_FILE = DATA_DIR + "prescriptions.dat";
    private static final String BILLS_FILE = DATA_DIR + "bills.dat";
    private static final String FEEDBACK_FILE = DATA_DIR + "feedback.dat";
    private static final String PAYMENTS_FILE = DATA_DIR + "payments.dat";

    public DataManager() {
        initializeDataDirectory();
    }

    private void initializeDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Generic save method using extensible collections
    private <T> void saveData(List<T> dataList, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(dataList);
        }
    }

    // Generic load method using extensible collections
    @SuppressWarnings("unchecked")
    private <T> List<T> loadData(String filename) throws IOException, ClassNotFoundException {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        }
    }

    // Patient operations
    public void savePatients(List<Patient> patients) throws IOException {
        saveData(patients, PATIENTS_FILE);
    }

    public List<Patient> loadPatients() throws IOException, ClassNotFoundException {
        return loadData(PATIENTS_FILE);
    }

    // Provider operations
    public void saveProviders(List<Provider> providers) throws IOException {
        saveData(providers, PROVIDERS_FILE);
    }

    public List<Provider> loadProviders() throws IOException, ClassNotFoundException {
        return loadData(PROVIDERS_FILE);
    }

    // Administrator operations
    public void saveAdministrators(List<Administrator> admins) throws IOException {
        saveData(admins, ADMINS_FILE);
    }

    public List<Administrator> loadAdministrators() throws IOException, ClassNotFoundException {
        return loadData(ADMINS_FILE);
    }

    // Appointment operations
    public void saveAppointments(List<Appointment> appointments) throws IOException {
        saveData(appointments, APPOINTMENTS_FILE);
    }

    public List<Appointment> loadAppointments() throws IOException, ClassNotFoundException {
        return loadData(APPOINTMENTS_FILE);
    }

    // EHR operations
    public void saveEHRs(List<EHR> ehrs) throws IOException {
        saveData(ehrs, EHRS_FILE);
    }

    public List<EHR> loadEHRs() throws IOException, ClassNotFoundException {
        return loadData(EHRS_FILE);
    }

    // Prescription operations
    public void savePrescriptions(List<Prescription> prescriptions) throws IOException {
        saveData(prescriptions, PRESCRIPTIONS_FILE);
    }

    public List<Prescription> loadPrescriptions() throws IOException, ClassNotFoundException {
        return loadData(PRESCRIPTIONS_FILE);
    }

    // Bill operations
    public void saveBills(List<Bill> bills) throws IOException {
        saveData(bills, BILLS_FILE);
    }

    public List<Bill> loadBills() throws IOException, ClassNotFoundException {
        return loadData(BILLS_FILE);
    }

    // Feedback operations
    public void saveFeedback(List<Feedback> feedbackList) throws IOException {
        saveData(feedbackList, FEEDBACK_FILE);
    }

    public List<Feedback> loadFeedback() throws IOException, ClassNotFoundException {
        return loadData(FEEDBACK_FILE);
    }

    // Payment operations
    public void savePayments(List<Payment> payments) throws IOException {
        saveData(payments, PAYMENTS_FILE);
    }

    public List<Payment> loadPayments() throws IOException, ClassNotFoundException {
        return loadData(PAYMENTS_FILE);
    }

    // Search operations
    public Patient searchPatientById(String patientId) throws IOException, ClassNotFoundException {
        List<Patient> patients = loadPatients();
        if (patients == null || patients.isEmpty()) {
            return null;
        }
        for (Patient patient : patients) {
            if (patient != null && patient.getUserId() != null &&
                    patient.getUserId().trim().equals(patientId.trim())) {
                return patient;
            }
        }
        return null;
    }

    public Provider searchProviderById(String providerId) throws IOException, ClassNotFoundException {
        List<Provider> providers = loadProviders();
        if (providers == null || providers.isEmpty()) {
            return null;
        }
        for (Provider provider : providers) {
            if (provider != null && provider.getUserId() != null &&
                    provider.getUserId().trim().equals(providerId.trim())) {
                return provider;
            }
        }
        return null;
    }

    public List<Appointment> searchAppointmentsByPatient(String patientId)
            throws IOException, ClassNotFoundException {
        List<Appointment> appointments = loadAppointments();
        List<Appointment> result = new ArrayList<>();
        for (Appointment apt : appointments) {
            if (apt.getPatientId().equals(patientId)) {
                result.add(apt);
            }
        }
        return result;
    }

    public List<Appointment> searchAppointmentsByProvider(String providerId)
            throws IOException, ClassNotFoundException {
        List<Appointment> appointments = loadAppointments();
        List<Appointment> result = new ArrayList<>();
        for (Appointment apt : appointments) {
            if (apt.getProviderId().equals(providerId)) {
                result.add(apt);
            }
        }
        return result;
    }
}