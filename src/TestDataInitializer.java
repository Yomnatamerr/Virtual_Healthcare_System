import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TestDataInitializer - Creates sample data for testing
 * Run this once to populate the system with test data
 */
public class TestDataInitializer {

    public static void main(String[] args) {
        DataManager dataManager = new DataManager();

        try {
            System.out.println("Initializing test data for VirtualCare 2025...");

            // Create sample administrator
            List<Administrator> admins = new ArrayList<>();
            Administrator admin = new Administrator("Emma Watson", "emma@virtualcare.com",
                    "555-0001", "admin123", "System Administration");
            admins.add(admin);
            dataManager.saveAdministrators(admins);
            System.out.println("✓ Created Admin - ID: " + admin.getUserId() + ", Password: admin123");

            // Create sample providers
            List<Provider> providers = new ArrayList<>();
            Provider dr1 = new Provider("Dr. John Smith", "drsmith@virtualcare.com",
                    "555-0002", "doc123", "Cardiology", "Mon-Fri 9AM-5PM");
            Provider dr2 = new Provider("Nurse Lucy Brown", "lucy@virtualcare.com",
                    "555-0003", "nurse123", "General Medicine", "Mon-Fri 8AM-4PM");
            Provider dr3 = new Provider("Dr. Sarah Johnson", "sarah@virtualcare.com",
                    "555-0004", "doc456", "Pediatrics", "Tue-Sat 10AM-6PM");

            providers.add(dr1);
            providers.add(dr2);
            providers.add(dr3);
            dataManager.saveProviders(providers);

            System.out.println("✓ Created Provider - ID: " + dr1.getUserId() +
                    ", Name: " + dr1.getName() + ", Password: doc123");
            System.out.println("✓ Created Provider - ID: " + dr2.getUserId() +
                    ", Name: " + dr2.getName() + ", Password: nurse123");
            System.out.println("✓ Created Provider - ID: " + dr3.getUserId() +
                    ", Name: " + dr3.getName() + ", Password: doc456");

            // Create sample patients
            List<Patient> patients = new ArrayList<>();
            Patient patient1 = new Patient("John Doe", "john@email.com",
                    "555-1001", "patient123", "Hypertension, Diabetes", "555-9999");
            Patient patient2 = new Patient("Jane Smith", "jane@email.com",
                    "555-1002", "patient456", "Asthma", "555-8888");

            patients.add(patient1);
            patients.add(patient2);
            dataManager.savePatients(patients);

            System.out.println("✓ Created Patient - ID: " + patient1.getUserId() +
                    ", Name: " + patient1.getName() + ", Password: patient123");
            System.out.println("✓ Created Patient - ID: " + patient2.getUserId() +
                    ", Name: " + patient2.getName() + ", Password: patient456");

            // Create EHRs for patients
            List<EHR> ehrs = new ArrayList<>();
            EHR ehr1 = new EHR(patient1.getEhrId(), patient1.getUserId());
            ehr1.addDiagnosis("Hypertension - Stage 1");
            ehr1.addDiagnosis("Type 2 Diabetes");
            ehr1.addTreatment("Blood pressure monitoring");
            ehr1.addTreatment("Diet and exercise plan");

            EHR ehr2 = new EHR(patient2.getEhrId(), patient2.getUserId());
            ehr2.addDiagnosis("Mild Asthma");
            ehr2.addTreatment("Inhaler as needed");

            ehrs.add(ehr1);
            ehrs.add(ehr2);
            dataManager.saveEHRs(ehrs);
            System.out.println("✓ Created EHRs for patients");

            // Create sample appointments
            List<Appointment> appointments = new ArrayList<>();
            Appointment apt1 = new Appointment(patient1.getUserId(), dr1.getUserId(),
                    LocalDateTime.now().plusDays(2), "General Consultation");
            Appointment apt2 = new Appointment(patient2.getUserId(), dr2.getUserId(),
                    LocalDateTime.now().plusDays(3), "Follow-up");

            appointments.add(apt1);
            appointments.add(apt2);
            dataManager.saveAppointments(appointments);
            System.out.println("✓ Created sample appointments");

            // Create sample prescriptions
            List<Prescription> prescriptions = new ArrayList<>();
            Prescription rx1 = new Prescription(patient1.getUserId(), dr1.getUserId(),
                    "Lisinopril", "10mg", "Once daily", "Take with food in the morning");
            Prescription rx2 = new Prescription(patient2.getUserId(), dr2.getUserId(),
                    "Albuterol Inhaler", "90mcg", "As needed", "Use when experiencing shortness of breath");

            prescriptions.add(rx1);
            prescriptions.add(rx2);
            dataManager.savePrescriptions(prescriptions);
            System.out.println("✓ Created sample prescriptions");

            // Create sample bills
            List<Bill> bills = new ArrayList<>();
            Bill bill1 = new Bill(patient1.getUserId(), apt1.getAppointmentId(),
                    150.00, "General Consultation - Cardiology");
            Bill bill2 = new Bill(patient2.getUserId(), apt2.getAppointmentId(),
                    100.00, "Follow-up Consultation");

            bills.add(bill1);
            bills.add(bill2);
            dataManager.saveBills(bills);
            System.out.println("✓ Created sample bills");

            System.out.println("\n========================================");
            System.out.println("TEST DATA INITIALIZATION COMPLETE!");
            System.out.println("========================================");
            System.out.println("\nYou can now login with:");
            System.out.println("\nADMIN:");
            System.out.println("  ID: " + admin.getUserId() + " | Password: admin123");
            System.out.println("\nPROVIDERS:");
            System.out.println("  ID: " + dr1.getUserId() + " | Password: doc123");
            System.out.println("  ID: " + dr2.getUserId() + " | Password: nurse123");
            System.out.println("  ID: " + dr3.getUserId() + " | Password: doc456");
            System.out.println("\nPATIENTS:");
            System.out.println("  ID: " + patient1.getUserId() + " | Password: patient123");
            System.out.println("  ID: " + patient2.getUserId() + " | Password: patient456");
            System.out.println("\nNow run: java VirtualCareGUI");

        } catch (IOException e) {
            System.err.println("Error initializing test data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}