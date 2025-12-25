import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Electronic Health Record class
 * Contains patient's medical records, diagnoses, and prescriptions
 */
public class EHR implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ehrId;
    private String patientId;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> prescriptionIds;
    private String lastUpdated;

    public EHR() {
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptionIds = new ArrayList<>();
    }

    public EHR(String ehrId, String patientId) {
        this.ehrId = ehrId;
        this.patientId = patientId;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.prescriptionIds = new ArrayList<>();
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    public void addDiagnosis(String diagnosis) {
        this.diagnoses.add(diagnosis);
        updateTimestamp();
    }

    public void addTreatment(String treatment) {
        this.treatments.add(treatment);
        updateTimestamp();
    }

    public void addPrescription(String prescriptionId) {
        this.prescriptionIds.add(prescriptionId);
        updateTimestamp();
    }

    private void updateTimestamp() {
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getEhrId() {
        return ehrId;
    }

    public void setEhrId(String ehrId) {
        this.ehrId = ehrId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<String> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<String> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public List<String> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<String> treatments) {
        this.treatments = treatments;
    }

    public List<String> getPrescriptionIds() {
        return prescriptionIds;
    }

    public void setPrescriptionIds(List<String> prescriptionIds) {
        this.prescriptionIds = prescriptionIds;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "EHR ID: " + ehrId + ", Patient: " + patientId +
                ", Diagnoses: " + diagnoses.size() + ", Treatments: " + treatments.size() +
                ", Prescriptions: " + prescriptionIds.size() + ", Last Updated: " + lastUpdated;
    }
}