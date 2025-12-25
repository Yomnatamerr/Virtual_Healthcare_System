import java.io.Serializable;
import java.time.LocalDate;

/**
 * Feedback class
 * Manages patient feedback and ratings for providers and services
 */
public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int feedbackCounter = 8000;

    private String feedbackId;
    private String patientId;
    private String providerId;
    private String appointmentId;
    private int rating; // 1-5 stars
    private String comments;
    private LocalDate submissionDate;

    public Feedback() {
    }

    public Feedback(String patientId, String providerId, String appointmentId,
                    int rating, String comments) {
        this.feedbackId = generateFeedbackId();
        this.patientId = patientId;
        this.providerId = providerId;
        this.appointmentId = appointmentId;
        this.rating = rating;
        this.comments = comments;
        this.submissionDate = LocalDate.now();
    }

    private static String generateFeedbackId() {
        return "FB" + (feedbackCounter++);
    }

    public static void setFeedbackCounter(int counter) {
        feedbackCounter = counter;
    }

    // Getters and Setters
    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public String toString() {
        return "Feedback ID: " + feedbackId + ", Provider: " + providerId +
                ", Rating: " + rating + "/5, Date: " + submissionDate +
                ", Comments: " + comments;
    }
}