// EHR.java
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EHR implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ehrId;
    private String patientId;
    private String patientName;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<Prescription> prescriptions;
    private String lastUpdated;

    public EHR() {
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    public EHR(String patientId, String patientName) {
        this.ehrId = "EHR" + patientId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getEhrId() { return ehrId; }
    public void setEhrId(String ehrId) { this.ehrId = ehrId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public List<String> getDiagnoses() { return diagnoses; }
    public void setDiagnoses(List<String> diagnoses) { this.diagnoses = diagnoses; }

    public List<String> getTreatments() { return treatments; }
    public void setTreatments(List<String> treatments) { this.treatments = treatments; }

    public List<Prescription> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }

    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
        updateTimestamp();
    }

    public void addTreatment(String treatment) {
        treatments.add(treatment);
        updateTimestamp();
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
        updateTimestamp();
    }

    public void updateRecord(String diagnosis, String treatment) {
        addDiagnosis(diagnosis);
        addTreatment(treatment);
    }

    private void updateTimestamp() {
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return "EHR ID: " + ehrId + ", Patient: " + patientName +
                ", Diagnoses: " + diagnoses.size() +
                ", Treatments: " + treatments.size() +
                ", Prescriptions: " + prescriptions.size() +
                ", Last Updated: " + lastUpdated;
    }
}