// Feedback.java
import java.io.Serializable;

public class Feedback implements Serializable {
    private static final long serialVersionUID = 1L;

    private String feedbackId;
    private String patientId;
    private String providerId;
    private int rating; // 1-5 stars
    private String comments;
    private String dateSubmitted;

    public Feedback() {}

    public Feedback(String feedbackId, String patientId, String providerId,
                    int rating, String comments) {
        this.feedbackId = feedbackId;
        this.patientId = patientId;
        this.providerId = providerId;
        this.rating = rating;
        this.comments = comments;
        this.dateSubmitted = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getFeedbackId() { return feedbackId; }
    public void setFeedbackId(String feedbackId) { this.feedbackId = feedbackId; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public int getRating() { return rating; }
    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        }
    }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getDateSubmitted() { return dateSubmitted; }
    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public void submitFeedback() {
        this.dateSubmitted = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return "Feedback ID: " + feedbackId + ", Patient: " + patientId +
                ", Provider: " + providerId + ", Rating: " + rating + "/5" +
                ", Comments: " + comments + ", Date: " + dateSubmitted;
    }
}