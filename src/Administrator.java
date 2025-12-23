// Administrator.java
public class Administrator extends User {
    private static final long serialVersionUID = 1L;

    private String adminId;
    private String department;

    public Administrator() {
        super();
    }

    public Administrator(String userId, String name, String email, String password,
                         String phoneNumber, String adminId, String department) {
        super(userId, name, email, password, phoneNumber);
        this.adminId = adminId;
        this.department = department;
    }

    // Getters and Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String getRole() {
        return "Administrator";
    }

    public void manageUsers() {
        System.out.println("Managing users...");
    }

    public void generateReports() {
        System.out.println("Generating system reports...");
    }

    public void configureSystem() {
        System.out.println("Configuring system settings...");
    }

    @Override
    public String toString() {
        return super.toString() + ", Admin ID: " + adminId +
                ", Department: " + department;
    }
}